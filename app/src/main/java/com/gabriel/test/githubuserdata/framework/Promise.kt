package com.gabriel.test.githubuserdata.framework

class Promise<Value> {

    private var value: Value? = null
    private var error: Throwable? = null

    private var valueConsumer: ((Value) -> Unit)? = null
    private var errorConsumer: ((Throwable) -> Unit)? = null

    fun success(value: Value) {
        this.value = value
        valueConsumer?.let {
            it(value)
        }
    }

    fun error(error: Throwable) {
        this.error = error
        errorConsumer?.let {
            it(error)
        }
    }

    fun onSuccess(valueConsumer: (Value) -> Unit): Promise<Value> {
        value?.let {
            valueConsumer(it)
        }
        this.valueConsumer = valueConsumer
        return this
    }

    fun onError(errorConsumer: (Throwable) -> Unit): Promise<Value> {
        error?.let {
            errorConsumer(it)
        }
        this.errorConsumer = errorConsumer
        return this
    }
}
