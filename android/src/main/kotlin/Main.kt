package org.example

import co.nstant.`in`.cbor.CborDecoder
import co.nstant.`in`.cbor.model.*
import co.nstant.`in`.cbor.model.Array
import co.nstant.`in`.cbor.model.Map
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.util.*


fun main() {
    val vcData = """{
    "issuanceDate": "2024-02-20T13:10:43.577Z",
    "credentialSubject": {
      "face": "",
       "gender": [
        {
          "language": "eng",
          "value": "Male"
        }
      ],
      "phone": "6748398732",
      "city": [
        {
          "language": "eng",
          "value": "Rabat"
        }
      ],
      "fullName": [
        {
          "language": "eng",
          "value": "azad ali"
        }
      ],
      "addressLine1": [
        {
          "language": "eng",
          "value": "yruuuiidh"
        }
      ],
      "dateOfBirth": "1998/02/12",
      "id": "did",
      "UIN": "2185943029",
      "email": "bvndhj@gamil.com"
    },
    "id": "https://ida.mosip.net/credentials/ce3b65bd-fe21-469b-a5fe-52bf219c1830",
    "proof": {
      "type": "RsaSignature2018",
      "created": "2024-02-20T13:10:43Z",
      "proofPurpose": "assertionMethod",
      "verificationMethod": "https://api.qa-inji1.mosip.net/.well-known/ida-public-key.json",
      "jws": "jws"
    },
    "type": [
      "VerifiableCredential",
      "MOSIPVerifiableCredential"
    ],
    "@context": [
      "https://www.w3.org/2018/credentials/v1",
      "https://api.qa-inji1.mosip.net/.well-known/mosip-ida-context.json"
    ],
    "issuer": "https://api.qa-inji1.mosip.net/.well-known/ida-controller.json"
  }"""
    val vcDataAsCBORHex =
        "a76c69737375616e6365446174657818323032342d30322d32305431333a31303a34332e3537375a7163726564656e7469616c5375626a656374aa6466616365606667656e64657281a2686c616e677561676563656e676576616c7565644d616c656570686f6e656a36373438333938373332646369747981a2686c616e677561676563656e676576616c75656552616261746866756c6c4e616d6581a2686c616e677561676563656e676576616c756568617a616420616c696c616464726573734c696e653181a2686c616e677561676563656e676576616c7565697972757575696964686b646174654f6642697274686a313939382f30322f31326269647902646469643a6a776b3a65794a7264486b694f694a53553045694c434a6c496a6f695156464251694973496e567a5a534936496e4e705a794973496d74705a434936496b6b3252574d345a46684a5156567651334a465a6d52464e6b52344e4538344f47786b5a575a4d526b644256444e5a617a453158334e47576c55694c434a68624763694f694a53557a49314e694973496d34694f694a7a5a476876576c6b7951544978576a4e73656b7835655856785a6e4668534846794d305a7a56486849635531314e33457a4d56564265554e45656e5a615554464c6555564d5930646657565a7853336734576a565753565a34646d6335647a6c7a516c6c31625764355a6a5a706130565256466f7464445a43646e52455a6a6861546b70535232597764325a5457575a52576c6c594f456c6f5a455a6b626e70794d47593155546c59656a683462544e7363466c446156557452477035646d3879575664305747644e4c56557757454a31613268496246704759544d305a32745155303970536e564759335271613235745633466e63556476656a4530567a63345930314864577732537a6c364e6a4e42596c4a4c5155396b646d68534f55526a54574630555863336369315554546c544d6e4a764e3278504f557479575459744d56647651553170615656746155744c5344645a526c68705a554e735a32356d54456c44516e70754e446b784d6c56305356564a634739584d554e5a565467774d30523057533033553039766357685352314674616a52614e464e68516b6851627a6334646e4276576d644e516e52764e7a524565444e4f613256724e325a70576b4e35636e636966513d3d6355494e6a3231383539343330323965656d61696c7062766e64686a4067616d696c2e636f6d626964784668747470733a2f2f6964612e6d6f7369702e6e65742f63726564656e7469616c732f63653362363562642d666532312d343639622d613566652d3532626632313963313833306570726f6f66a56474797065705273615369676e617475726532303138676372656174656474323032342d30322d32305431333a31303a34335a6c70726f6f66507572706f73656f617373657274696f6e4d6574686f6472766572696669636174696f6e4d6574686f64783e68747470733a2f2f6170692e71612d696e6a69312e6d6f7369702e6e65742f2e77656c6c2d6b6e6f776e2f6964612d7075626c69632d6b65792e6a736f6e636a77737901d665794a694e6a51694f6d5a6862484e6c4c434a6a636d6c30496a7062496d49324e434a644c434a72615751694f694a6b634852564d305235593252765556526a555535614f57354462457066574639444d553936626d564255484e455a6d6b31564331745a6a6734496977695957786e496a6f6955464d794e54596966512e2e64766c435f77764373554c634161666a69444d384b3245416968715869725a3863364e6e62376e4b72336871702d45552d425177536135437566617a663470624836535651305a32644e3132534d6864584e36563570532d64527051376779467938503032466e3077393851312d474d4649503276363665557373387379336e3475776178547435766b383138744256735f47436445784b66584a516f34534770475a66582d5a53417a484137415a4478594a4a6c4275734c4e6533446a70552d376f746556506b744b50444650474d7a5134736f2d304d67473139495a4750666e5a5245775a586b5a566c414168726f324850414a314b4a6847674b6650374d364e5a6f6d58314d326a356f35413537735534657a667052754a72616162796f3059534c51326d656a4246746459794f756c7a686b71627048484e6e5437724d6c524a795a78744637437039644e717157596f69776474797065827456657269666961626c6543726564656e7469616c78194d4f53495056657269666961626c6543726564656e7469616c6840636f6e7465787482782668747470733a2f2f7777772e77332e6f72672f323031382f63726564656e7469616c732f7631784168747470733a2f2f6170692e71612d696e6a69312e6d6f7369702e6e65742f2e77656c6c2d6b6e6f776e2f6d6f7369702d6964612d636f6e746578742e6a736f6e66697373756572783e68747470733a2f2f6170692e71612d696e6a69312e6d6f7369702e6e65742f2e77656c6c2d6b6e6f776e2f6964612d636f6e74726f6c6c65722e6a736f6e".decodeHex()
    val vcDataItemArray = CborDecoder(ByteArrayInputStream(vcDataAsCBORHex)).decode()
    val vcDataItem = vcDataItemArray[0]

    val gson =  GsonBuilder()
        .setPrettyPrinting()
        .create()
    val json = gson.toJson(vcDataItem)
   /// println(json)


    val vcDataItemToJson = vcDataItem.toJson()
    println(vcDataItemToJson.toString())

    /*val t = gson.toJson(vcDataItemToJson)
    println(t)*/

}

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

fun DataItem.toJson(): Any {
    return if (this.majorType == MajorType.MAP)
        mapToJson(JSONObject(), this as Map)
    else
        arrayToJson(JSONArray(), this as Array)
}

private fun mapToJson(accumulator: JSONObject, map: Map): JSONObject {
    val iterator: Iterator<DataItem> = map.keys.iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        val key = next.toString()
        val dataItem = map.get(next)
        when (dataItem.majorType) {
            MajorType.MAP -> accumulator.put(key, mapToJson(JSONObject(), dataItem as Map))
            MajorType.ARRAY -> accumulator.put(key, arrayToJson(JSONArray(), dataItem as Array))
            MajorType.UNICODE_STRING -> accumulator.put(key, (dataItem as UnicodeString).string)
            MajorType.UNSIGNED_INTEGER,
            MajorType.NEGATIVE_INTEGER -> accumulator.put(key, (dataItem.toString()).toInt())
            MajorType.BYTE_STRING -> accumulator.put(key, dataItem as ByteString)
            MajorType.SPECIAL -> accumulator.put(key, getSpecial(dataItem.toString()))
            MajorType.INVALID -> accumulator.put(key, JSONObject.NULL)
            else -> accumulator.put(key, dataItem)
        }
    }
    return accumulator
}

private fun arrayToJson(accumulator: JSONArray, array: Array): JSONArray {
    for (dataItem in array.dataItems) {
        when (dataItem.majorType) {
            MajorType.MAP -> accumulator.put(mapToJson(JSONObject(), dataItem as Map))
            MajorType.ARRAY -> accumulator.put(arrayToJson(JSONArray(), dataItem as Array))
            MajorType.UNICODE_STRING -> accumulator.put((dataItem as UnicodeString).string)
            MajorType.UNSIGNED_INTEGER,
            MajorType.NEGATIVE_INTEGER -> accumulator.put((dataItem.toString()).toInt())
            MajorType.BYTE_STRING -> accumulator.put(dataItem as ByteString)
            MajorType.SPECIAL -> accumulator.put(getSpecial(dataItem.toString()))
            MajorType.INVALID -> accumulator.put(JSONObject.NULL)
            else -> accumulator.put(dataItem)
        }
    }
    return accumulator
}

private fun getSpecial(get: String): Any? {
    return try {
        get.toFloat()
    } catch (_: Exception) {
        try {
            if (get.lowercase(Locale.getDefault())
                    .toBooleanStrictOrNull() == null
            ) JSONObject.NULL else get.lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (_: Exception) {
            return JSONObject.NULL
        }
    }
}



