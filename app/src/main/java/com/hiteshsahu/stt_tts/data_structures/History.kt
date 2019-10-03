package com.hiteshsahu.stt_tts.data_structures

class History {

    private var history = mutableListOf<String>()
    private var JSON = "{}"

    fun add(label: String, value: String) {
        JSON = JSON.substring(0, JSON.length - 1)
        JSON += ",\"$label\":\"$value\"}"
    }

    fun startCard(label: String, value: String) {
        JSON = "{\"$label\":\"$value\"}"
    }

    fun endCard() {
        history.add(JSON)
    }

    fun contain(label: String, value: String): Boolean {
        for (card in history) {
            val attributes = card.split(",")
            for (field in attributes) {
                val fieldSplit = field.split(":")
                val fieldLabel = fieldSplit[0].replace("\"", "").replace("{", "").replace("}", "")
                val fieldValue = fieldSplit[1].replace("\"", "").replace("{", "").replace("}", "")
                if (fieldLabel == label && fieldValue == value) {
                    return true
                }
            }
        }
        return false
    }

    fun contain(label1: String, value1: String, label2: String, value2: String): Boolean {
        for (card in history) {
            val attributes = card.split(",")
            for (field1 in attributes) {
                val field1Split = field1.split(":")
                val fieldLabel1 = field1Split[0].replace("\"", "").replace("{", "").replace("}", "")
                val fieldValue1 = field1Split[1].replace("\"", "").replace("{", "").replace("}", "")
                if (fieldLabel1 == label1 && fieldValue1 == value1) {
                    for (field2Split in attributes) {
                        val attrSplit2 = field2Split.split(":")
                        val fieldLabel2 = attrSplit2[0].replace("\"", "").replace("{", "").replace("}", "")
                        val fieldValue2 = attrSplit2[1].replace("\"", "").replace("{", "").replace("}", "")
                        if (fieldLabel2 == label2 && fieldValue2 == value2) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun print(): String {
        var string = "{"
        for (card in history) {
            string += "$card,"
        }
        string = string.substring(0, string.length - 1)
        string += "}"
        return string
    }
}