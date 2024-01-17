package com.ilifesmart.nanohttp

import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.io.FileInputStream

class MyNanoHttp(port:Int, private var rootPath:String): NanoHTTPD(port) {

    private val fileNotExisted = """{"code": 1, "msg": "文件不存在"}"""
    private val invalidParams = """{"code": 2, "msg": "参数错误"}"""

    override fun serve(session: IHTTPSession?): Response {
        val uri = session?.uri
        if (uri?.isNotEmpty() == true && uri.startsWith("/mgapkg/")) {
            return try {
                val filePath = "$rootPath${uri.substring("/mgapkg".length)}"
                val file = File(filePath)

                if (file.exists()) {
                    newFixedLengthResponse(Response.Status.OK, getMimeTypeForFile(filePath), FileInputStream(filePath), file.length())
                } else {
                    newFixedLengthResponse(Response.Status.BAD_REQUEST, "application/json", fileNotExisted)
                }
            } catch (ex:Throwable) {
                newFixedLengthResponse(Response.Status.BAD_REQUEST, "application/json", fileNotExisted)
            }
        }

        return newFixedLengthResponse(Response.Status.BAD_REQUEST, "application/json", invalidParams)
    }
}