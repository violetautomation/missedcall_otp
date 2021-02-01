# missedcall_otp

1. Add it in your root build.gradle at the end of repositories:

allprojects 

    {
	    repositories 
	        {
			    maven { url 'https://jitpack.io' }	
	    	}
	}
 
  
2. Add the dependency


        dependencies 
            {
                implementation 'com.github.vikramarora85:missedcall_otp:0.1.2'
            }
  
  
3. How to Use:


        class HomeActivity : AppCompatActivity() 
            {
            override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)

            initView()
            initListener()
            }

        private fun initView() {
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(
            onNotice,
            IntentFilter(Constants.FLASH_BROADCAST_ACTION))
                }

         private fun initListener() {
             verify.setOnClickListener {
            it.preventDoubleClick()
            if (isReadLogStetePermissionGranted(26)) {
                missCallVerification()
                    }
                }
         }

        private fun missCallVerification() {
            ServiceData.callAlert(
                 countryCode.text.toString().trim(),
                 mobile.text.toString().trim(),
                 getString(R.string.api_key),
                             "userId"
                ) //country code like 91, mobile any valid 10 digit number
          }

        private val onNotice: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                ServiceData.verifyCall {
                    if (it) {
                        runOnUiThread {
                            Toast.makeText(this@HomeActivity, "Number Verified!", Toast.LENGTH_SHORT)
                            .show()
                             }
                        }
                    }
                }
         }

         override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
            ) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                when (requestCode) {
                 26 -> {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //open screen
                    missCallVerification()
                    } else {
                    // Permission Denied.
                        }
                      }
                  }
            }

             override fun onDestroy() {
                 LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice)
                    super.onDestroy()
                             }
                }
