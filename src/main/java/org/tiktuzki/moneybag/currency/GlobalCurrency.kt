package org.tiktuzki.moneybag.currency

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class GlobalCurrencyConverter : AttributeConverter<GlobalCurrency, String> {
    override fun convertToDatabaseColumn(attribute: GlobalCurrency): String? {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: String?): GlobalCurrency? {
        return dbData?.let { GlobalCurrency(it) }
    }
}

data class GlobalCurrency(
    val code: String,
)