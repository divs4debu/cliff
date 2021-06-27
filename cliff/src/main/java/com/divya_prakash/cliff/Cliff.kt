package com.divya_prakash.cliff

import android.content.Context
import android.widget.ImageView
import com.divya_prakash.cliff.processor.ArgumentProcessor
import kotlinx.coroutines.*

class Cliff {
    private val arguments = Arguments()
    private val coroutineScope= CoroutineScope(Dispatchers.IO)

    fun load(url: String): Cliff = this.apply { arguments.addUrl(url) }

    fun into(view: ImageView) {
        arguments.addView(view)
        coroutineScope.launch {
            ArgumentProcessor(arguments).process()
        }
    }
    companion object {
        fun with(context: Context): Cliff = Cliff().apply {
            arguments.addContext(context)
        }
    }

    class Arguments {
        internal lateinit var context: Context
        internal lateinit var url: String
        internal lateinit var view: ImageView

        internal fun addContext(context: Context) {
            this.context = context
        }

        internal fun addUrl(url: String) {
            this.url = url
        }

        internal fun addView(view: ImageView) {
            this.view = view
        }
    }
}