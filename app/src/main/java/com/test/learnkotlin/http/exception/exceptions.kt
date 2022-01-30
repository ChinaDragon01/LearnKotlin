package com.test.learnkotlin.http.exception

import com.test.learnkotlin.http.ResponseEntity
import java.io.IOException

class NetErrorException : IOException("The network is unavailable.")

/**
 * Token校验异常
 */
class TokenCheckException(message: String?, val entity: ResponseEntity<*>) : IOException(message)