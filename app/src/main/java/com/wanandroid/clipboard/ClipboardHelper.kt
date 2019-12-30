package com.wanandroid.clipboard

import android.content.*
import android.net.Uri
import com.ilifesmart.ToolsApplication

class ClipboardHelper private constructor(private val mContext: Context?) {
    private val mClipboardManager: ClipboardManager
    /**
     * 判断剪贴板内是否有数据
     *
     * @return
     */
    fun hasPrimaryClip(): Boolean {
        return mClipboardManager.hasPrimaryClip()
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @return
     */
    val clipText: String?
        get() {
            if (!hasPrimaryClip()) {
                return null
            }
            val data = mClipboardManager.primaryClip
            return if (data != null
                && mClipboardManager.primaryClipDescription!!.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            ) {
                data.getItemAt(0).text.toString()
            } else null
        }

    /**
     * 获取剪贴板中第一条String
     *
     * @param context
     * @return
     */
    fun getClipText(context: Context?): String? {
        return getClipText(context, 0)
    }

    /**
     * 获取剪贴板中指定位置item的string
     *
     * @param context
     * @param index
     * @return
     */
    fun getClipText(context: Context?, index: Int): String? {
        if (!hasPrimaryClip()) {
            return null
        }
        val data = mClipboardManager.primaryClip ?: return null
        return if (data.itemCount > index) {
            data.getItemAt(index).coerceToText(context).toString()
        } else null
    }

    /**
     * 将文本拷贝至剪贴板
     *
     * @param text
     */
    fun copyText(label: String?, text: String?) {
        val clip = ClipData.newPlainText(label, text)
        mClipboardManager.setPrimaryClip(clip)
    }

    /**
     * 将HTML等富文本拷贝至剪贴板
     *
     * @param label
     * @param text
     * @param htmlText
     */
    fun copyHtmlText(
        label: String?,
        text: String?,
        htmlText: String?
    ) {
        val clip = ClipData.newHtmlText(label, text, htmlText)
        mClipboardManager.setPrimaryClip(clip)
    }

    /**
     * 将Intent拷贝至剪贴板
     *
     * @param label
     * @param intent
     */
    fun copyIntent(label: String?, intent: Intent?) {
        val clip = ClipData.newIntent(label, intent)
        mClipboardManager.setPrimaryClip(clip)
    }

    /**
     * 将Uri拷贝至剪贴板
     * If the URI is a content: URI,
     * this will query the content provider for the MIME type of its data and
     * use that as the MIME type.  Otherwise, it will use the MIME type
     * [ClipDescription.MIMETYPE_TEXT_URILIST].
     * 如 uri = "content://contacts/people"，那么返回的MIME type将变成"vnd.android.cursor.dir/person"
     *
     * @param cr    ContentResolver used to get information about the URI.
     * @param label User-visible label for the clip data.
     * @param uri   The URI in the clip.
     */
    fun copyUri(cr: ContentResolver?, label: String?, uri: Uri?) {
        val clip = ClipData.newUri(cr, label, uri)
        mClipboardManager.setPrimaryClip(clip)
    }

    /**
     * 将多组数据放入剪贴板中，如选中ListView多个Item，并将Item的数据一起放入剪贴板
     *
     * @param label    User-visible label for the clip data.
     * @param mimeType mimeType is one of them:[ClipDescription.MIMETYPE_TEXT_PLAIN],
     * [ClipDescription.MIMETYPE_TEXT_HTML],
     * [ClipDescription.MIMETYPE_TEXT_URILIST],
     * [ClipDescription.MIMETYPE_TEXT_INTENT].
     * @param items    放入剪贴板中的数据
     */
    fun copyMultiple(
        label: String?,
        mimeType: String,
        items: List<ClipData.Item?>?
    ) {
        require(!(items == null || items.size == 0)) { "argument: items error" }
        val size = items.size
        val clip = ClipData(label, arrayOf(mimeType), items[0])
        for (i in 1 until size) {
            clip.addItem(items[i])
        }
        mClipboardManager.setPrimaryClip(clip)
    }

    fun copyMultiple(
        label: String?,
        mimeTypes: Array<String?>?,
        items: List<ClipData.Item?>?
    ) {
        require(!(items == null || items.size == 0)) { "argument: items error" }
        val size = items.size
        val clip = ClipData(label, mimeTypes, items[0])
        for (i in 1 until size) {
            clip.addItem(items[i])
        }
        mClipboardManager.setPrimaryClip(clip)
    }

    fun coercePrimaryClipToText(): CharSequence? {
        return if (!hasPrimaryClip()) {
            null
        } else mClipboardManager.primaryClip!!.getItemAt(0).coerceToText(mContext)
    }

    fun coercePrimaryClipToStyledText(): CharSequence? {
        return if (!hasPrimaryClip()) {
            null
        } else mClipboardManager.primaryClip!!.getItemAt(0).coerceToStyledText(mContext)
    }

    fun coercePrimaryClipToHtmlText(): CharSequence? {
        return if (!hasPrimaryClip()) {
            null
        } else mClipboardManager.primaryClip!!.getItemAt(0).coerceToHtmlText(mContext)
    }

    /**
     * 获取当前剪贴板内容的MimeType
     *
     * @return 当前剪贴板内容的MimeType
     */
    val primaryClipMimeType: String?
        get() = if (!hasPrimaryClip()) {
            null
        } else mClipboardManager.primaryClipDescription!!.getMimeType(0)

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clip 剪贴板内容
     * @return 剪贴板内容的MimeType
     */
    fun getClipMimeType(clip: ClipData): String {
        return clip.description.getMimeType(0)
    }

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clipDescription 剪贴板内容描述
     * @return 剪贴板内容的MimeType
     */
    fun getClipMimeType(clipDescription: ClipDescription): String {
        return clipDescription.getMimeType(0)
    }

    /**
     * 清空剪贴板
     */
    fun clearClip() {
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""))
    }

    val clipData: ClipData?
        get() = if (!hasPrimaryClip()) {
            null
        } else mClipboardManager.primaryClip

    companion object {
        val TAG = ClipboardHelper::class.java.simpleName
        @Volatile
        private var mInstance: ClipboardHelper? = null

        /**
         * 获取ClipboardUtil实例，记得初始化
         *
         * @return 单例
         */
        fun getInstance(context: Context): ClipboardHelper? {
            if (mInstance == null) {
                synchronized(ClipboardHelper::class.java) {
                    if (mInstance == null) {
                        mInstance = ClipboardHelper(context.applicationContext)
                    }
                }
            }
            return mInstance
        }

        val instance: ClipboardHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ClipboardHelper(ToolsApplication.getContext())
        }
    }

    init {
        mClipboardManager = mContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }
}