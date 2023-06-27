package com.ilifesmart.media;

import com.google.android.exoplayer2.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MediaCodecThread extends Thread {

	public static final String TAG = "MediaCodecThread";
	//解码器
	private MediaCodecUtil util;
	//文件路径
	private String path;
	//文件读取完成标识
	private boolean isFinish = false;
	//这个值用于找到第一个帧头后，继续寻找第二个帧头，如果解码失败可以尝试缩小这个值
	private static final int FRAME_MIN_LEN = 20;
	//一般H264帧大小不超过200k,如果解码失败可以尝试增大这个值
	private static final int FRAME_MAX_LEN = 300 * 1024;
	//根据帧率获取的解码每帧需要休眠的时间,根据实际帧率进行操作
	private static final int PRE_FRAME_TIME = 1000 / 25;
	//按帧用来缓存h264数据
	private ArrayList<byte[]> frameList;
	//缓存最多的帧数
	private static final int MAX_FRAME_SIZE = 100;


	// 此时并未判断codec.dequeueInputBuffer的返回值是否合适.
	/**
	 * 初始化解码器
	 *
	 * @param util 解码 Util
	 * @param path 文件路径
	 */
	public MediaCodecThread(MediaCodecUtil util, String path) {
		this.util = util;
		this.path = path;
		frameList = new ArrayList<>();
		//开启解码线程
		new DecodeThread().start();
	}

	/**
	 * 寻找指定 buffer 中 h264 头的开始位置
	 *
	 * @param data   数据
	 * @param offset 偏移量
	 * @param max    需要检测的最大值
	 * @return h264头的开始位置 ,-1表示未发现
	 */
	private int findHead(byte[] data, int offset, int max) {
		int i;
		for (i = offset; i <= max; i++) {
			//发现帧头
			if (isHead(data, i))
				break;
		}
		//检测到最大值，未发现帧头
		if (i == max) {
			i = -1;
		}
		return i;
	}

	/**
	 * 判断是否是I帧/P帧头:
	 * 00 00 00 01 65    (I帧) // 完整编码的帧
	 * 00 00 00 01 61 / 41   (P帧) // 参考前面I帧生成的仅包含差异部分编码的帧为P帧
	 * 00 00 00 01 67    (SPS)
	 * 00 00 00 01 68    (PPS)
	 *
	 * @param data   解码数据
	 * @param offset 偏移量
	 * @return 是否是帧头
	 */
	private boolean isHead(byte[] data, int offset) {
		boolean result = false;
		// 00 00 00 01 x
		if (data[offset] == 0x00 && data[offset + 1] == 0x00
						&& data[offset + 2] == 0x00 && data[3] == 0x01 && isVideoFrameHeadType(data[offset + 4])) {
			result = true;
		}
		// 00 00 01 x
		if (data[offset] == 0x00 && data[offset + 1] == 0x00
						&& data[offset + 2] == 0x01 && isVideoFrameHeadType(data[offset + 3])) {
			result = true;
		}
		return result;
	}

	/**
	 * I帧或者P帧
	 */
	private boolean isVideoFrameHeadType(byte head) {
		return head == (byte) 0x65 || head == (byte) 0x61 || head == (byte) 0x41
						|| head == (byte) 0x67 || head == (byte) 0x68;
	}

	private byte[] getBytes(File file) throws IOException {
		InputStream is = new DataInputStream(new FileInputStream(file));
		int len;
		int size = 1024;
		byte[] buf;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		buf = new byte[size];
		while ((len = is.read(buf, 0, size)) != -1)
			bos.write(buf, 0, len);
		buf = bos.toByteArray();

		is.close();
		return buf;
	}

	@Override
	public void run() {
		super.run();
		File file = new File(path);

		Log.d("TAG", "run: file.isExist " + file.exists() + " path " + path);
		//判断文件是否存在
		if (file.exists()) {


			try {
				byte[] bytes = getBytes(file);

				int offset = 0;
				while (true) {
					int index = -1;
					int first = 0;
					while ((index = findHead(bytes, offset, bytes.length)) != -1) {
						first = index;
						break;
					}

					int second = 0;
					while ((index = findHead(bytes, first+1, bytes.length)) != -1) {
						second = index;
						break;
					}

					android.util.Log.d(TAG, "run: bytes[1,2,3,4] = " + toHex(bytes[first]) + toHex(bytes[first+1]) + toHex(bytes[first+2]) + toHex(bytes[first+3]) + " Length：" + (second-first) + " first " + first);
					util.onFrame(bytes, first, second-first);

					offset = second-1;

					Thread.sleep(1000);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.e("TAG", "File not found");
		}
	}

	//视频解码
	private void onFrame(byte[] frame, int offset, int length) {
		if (util != null) {
			try {
//                long s = System.currentTimeMillis();
				util.onFrame(frame, offset, length);
//                Log.e("DecodeFileTime", id + " : " + (System.currentTimeMillis() - s));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.e("TAG", "mediaCodecUtil is NULL");
		}
	}

	//修眠
	private void sleepThread(long startTime, long endTime) {
		//根据读文件和解码耗时，计算需要休眠的时间
		long time = PRE_FRAME_TIME - (endTime - startTime);
		if (time > 0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	String toHex(byte num) {
		return String.format("%02x", num);
	}

	//将视频数据添加到缓存List
	private void addFrame(byte[] frame) throws InterruptedException {
		frameList.add(frame);

		if (frame.length == 4) {
			Log.d(TAG, "addFrame: [0,1,2,3,4] "+ toHex(frame[0])+ toHex(frame[1])+ toHex(frame[2])+ toHex(frame[3])+ toHex(frame[4]));
		} else if (frame.length == 3){
			Log.d(TAG, "addFrame: [0,1,2,3] "+ toHex(frame[0])+ toHex(frame[1])+ toHex(frame[2])+ toHex(frame[3]));
		}

		//当长度多于MAX_FRAME_SIZE时,休眠2秒，避免OOM
		if (frameList.size() > MAX_FRAME_SIZE) {
			Thread.sleep(2000);
		}
	}

	//手动终止读取文件，结束线程
	public void stopThread() {
		isFinish = true;
	}

	/**
	 * 解码线程
	 */
	private class DecodeThread extends Thread {
		@Override
		public void run() {
			super.run();
			long start;
			while (!isFinish || frameList.size() > 0) {
				start = System.currentTimeMillis();
				if (frameList != null && frameList.size() > 0) {
					onFrame(frameList.get(0), 0, frameList.get(0).length);
					//移除已经解码的数据
					frameList.remove(0);
				}
				//休眠
				sleepThread(start, System.currentTimeMillis());
			}
		}
	}
}