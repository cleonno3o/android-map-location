package campus.tech.kakao.map

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import java.lang.Exception

class MapActivity : AppCompatActivity() {
    private val TAG = "KAKAOMAP"
    private lateinit var kakaoMapView: MapView
    private lateinit var searchBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        kakaoMapView = findViewById(R.id.kakao_map_view)
        searchBox = findViewById(R.id.search_box)

        kakaoMapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
            }

            override fun onMapError(exception: Exception?) {
            }

        }, object : KakaoMapReadyCallback(){
            override fun onMapReady(kakaoMap: KakaoMap) {
                Log.d(TAG, "onMapReady")
                val targetLocation =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent?.extras?.getSerializable(Location.LOCATION, Location::class.java)
                    } else {
                        intent?.extras?.getSerializable(Location.LOCATION) as Location?
                    }

                targetLocation?.let {
                    val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(targetLocation.y, targetLocation.x))
                    kakaoMap.moveCamera(cameraUpdate)
                }
            }
        })

        searchBox.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        kakaoMapView.resume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        kakaoMapView.pause()
        Log.d("KAKAOMAP","onPause")
    }

}