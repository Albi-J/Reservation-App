package com.example.reservationapp

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.listener.CancelListener
import kr.co.bootpay.listener.CloseListener
import kr.co.bootpay.listener.ConfirmListener
import kr.co.bootpay.listener.DoneListener
import kr.co.bootpay.listener.ErrorListener
import kr.co.bootpay.listener.ReadyListener
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser


class PaymentActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val stuck = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
        BootpayAnalytics.init(this, "5de1041f0627a8002487cab8")

        onClick_request()
    }

    fun onClick_request()  {
        // 결제호출

        val bootUser : BootUser= BootUser().setPhone("010-1234-5678")
        val bootExtra  : BootExtra = BootExtra().setQuotas(IntArray(3){0;2;3})

        Bootpay.init(fragmentManager)
            .setApplicationId("5de1041f0627a8002487cab8") // 해당 프로젝트(안드로이드)의 application id 값

            .setPG(PG.INICIS) // 결제할 PG 사
            .setMethod(Method.CARD) // 결제수단
            .setContext(this)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
            .setName("storename") // 결제할 상품명
            .setOrderId("1234") // 결제 고유번호expire_month
            .setPrice(10000) // 결제할 금액

            .onDone( DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                fun onDone(@Nullable message :String) {
                    Log.d("done", message)
                }
            })
            .onReady(ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
               fun onReady(@Nullable message: String) {
                    Log.d("ready", message)
                }
            })
            .onCancel( CancelListener() { // 결제 취소시 호출
                fun onCancel(@Nullable message : String) {

                    Log.d("cancel", message)
                }
            })
            .onError( ErrorListener() { // 에러가 났을때 호출되는 부분
                 fun onError(@Nullable message : String) {
                    Log.d("error", message)
                }
            })
            .onClose(
                 CloseListener() { //결제창이 닫힐때 실행되는 부분
                     val intent = Intent(this, MainActivity::class.java)
                     startActivity(intent)
                     finish()
                    fun onClose(message : String) {
                        Log.d("close", "close")
                    }
                })
            .request()
    }
}
