package com.novina.amazeme.util

import android.content.res.Resources
import android.os.Build
import android.text.Html
import com.google.gson.JsonParseException
import com.novina.amazeme.R
import com.novina.amazeme.model.Show
import com.novina.amazeme.data.network.error.*
import com.novina.amazeme.ui.viewmodel.MAX_RATING
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException

fun Throwable.getDetailedError(): Throwable {
    return when (this) {
        is CancellationException -> this
        is UnknownHostException,
        is TimeoutException,
        is IOException,
        is SSLException -> NetworkException(this)
        is ConnectException -> InternetConnectionException(this)
        is JsonParseException -> ModelMappingException(this)
        is HttpException -> HttpRequestException(
            message = "${this.response()?.code()} ${
                this.response()?.message()
            }",
            response = this.response().toString(),
            cause = this,
            code = this.code()
        )
        else -> UnknownNetworkException(this)
    }
}

fun Throwable.localizedString(resource: Resources?): String {
    return resource?.let { res ->
        when (this.cause) {
            is UnknownHostException -> res.getString(R.string.connection_error_message)
            else -> res.getString(R.string.generic_network_error, this.message)
        }
    } ?: this.localizedMessage
}

fun Show?.ratingString(): String = this?.rating?.let { "$it / $MAX_RATING" } ?: "Not Available"

fun Show?.summaryString(): String {
    return this?.summary?.let { htmlSummary ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlSummary, Html.FROM_HTML_MODE_COMPACT).toString()
        } else Html.fromHtml(htmlSummary).toString()
    } ?: ""
}

fun Show?.genreString(): String = this?.genres?.let { genres -> genres.joinToString { it } } ?: ""