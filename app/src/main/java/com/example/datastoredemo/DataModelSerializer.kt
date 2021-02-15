package com.example.datastoredemo

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object DataModelSerializer : Serializer<DataModelPreference> {
    override fun readFrom(input: InputStream): DataModelPreference {
        try {
            return DataModelPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: DataModelPreference, output: OutputStream) {
        t.writeTo(output)
    }

    override val defaultValue: DataModelPreference
        get() = DataModelPreference.getDefaultInstance()
}