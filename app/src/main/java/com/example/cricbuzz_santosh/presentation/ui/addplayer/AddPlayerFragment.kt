package com.example.cricbuzz_santosh.presentation.ui.addplayer

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cricbuzz_santosh.R
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import com.example.cricbuzz_santosh.databinding.FragmentAddPlayerBinding
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModel
import com.example.cricbuzz_santosh.presentation.viewmodel.MainViewModelFactory
import java.io.ByteArrayOutputStream
import java.util.*


class AddPlayerFragment : Fragment() {

    private lateinit var vm:MainViewModel
    private lateinit var repository:PlayerRepository
    private lateinit var dataBase:PlayerDataBase
    private lateinit var dataBinding:FragmentAddPlayerBinding
    private lateinit var playerStyle:String
    var SELECT_PICTURE=100
    lateinit var selectedImageBitmap:Bitmap
    lateinit var calendar: Calendar
    var days:Int=0
    var month:Int=0
    var mmMonth:Int=0
    var year:Int=0
    var myearr:Int=0
    var date:String=""
    var imageSelected=false
    var byteArray:ByteArray?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG","AddPlayerFragment Calling")

        dataBinding = FragmentAddPlayerBinding.inflate(inflater)
        repository = PlayerRepository(PlayerDataBase.getDataBase(requireActivity().applicationContext))
        dataBase = PlayerDataBase.getDataBase(requireContext().applicationContext)
        vm = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(MainViewModel::class.java)

        // Inflate the layout for this fragment
        return dataBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //choosing image from the gallery
        dataBinding.ivProfilePicture.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                imageChooser()  //if permissions is already given call this method
            } else {
                Log.d(TAG, "Gallery permission is not granted, requesting for permission")
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(requireContext() as Activity, permissions, 1)
            }

        }

        dataBinding.ivCalender.setOnClickListener {
            calendar= Calendar.getInstance()
            year= calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            days = calendar.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                mmMonth = mMonth+1
                date = "$mDay/$mmMonth/$mYear"
                myearr=mYear
                dataBinding.tvCalender.text=date
            },year,month,days)
            dpd.show()
        }



        vm.player.observe(viewLifecycleOwner) {

            //saving data in the database
            dataBinding.btnSavePlayer.setOnClickListener {

                if(byteArray!=null){
                    Log.d("Check",selectedImageBitmap.toString())
                    saveData()
                }

                else
                    Toast.makeText(context, "Please choose an image", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveData() {

        //getting player Style
        getStyleFromRadioButton()

        val dob:String=date
        val image:ByteArray = byteArray!!
        val name:String = dataBinding.etNamePlayer.text.toString()
        val team:String = dataBinding.etTeam.text.toString()
        val country:String = dataBinding.etCountry.text.toString()
        val matches:Editable? = dataBinding.etMatches.text
        val runs:Editable? = dataBinding.etRuns.text
        val wickets: Editable? = dataBinding.etWickets.text
        val totalDays:Int
        val totalYears:Int
        val yearr:Int
        val mmonth:Int
        if(validateUser(dob,name,team,country,matches,runs,wickets) && selectedImageBitmap!=null && !selectedImageBitmap.equals(0)) {
            yearr=2022-myearr-1
            mmonth=12-mmMonth
            totalDays=(yearr*365)+(mmonth*30)+days
            Log.d("totalDays ",totalDays.toString())


            if(2022-myearr>=18) {
                val players = Player(
                    name = name, team = team, country = country,
                    profilePhoto = image, dob = dob, style = playerStyle,
                    matches = Integer.parseInt(matches.toString()),
                    runs = Integer.parseInt(runs.toString()),
                    wickets = Integer.parseInt(wickets.toString()), fav = 0
                )

                vm.addPlayer(players)

                findNavController().popBackStack()
//                Navigation.findNavController(requireView())
//                    .navigate(R.id.action_addPlayerFragment_to_homeFragment)

            }
            else
                Toast.makeText(context, "Player should be 18 years of age.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "All Fields Mandatory", Toast.LENGTH_SHORT).show()
        }

    }

    //validating the user info for empty and null values
    private fun validateUser(
        dob: String,
        name: String,
        team: String,
        country: String,
        matches: Editable?,
        runs: Editable?,
        wickets: Editable?
    ): Boolean {

        return !(TextUtils.isEmpty(dob.isEmpty().toString())
                || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(team) || TextUtils.isEmpty(country) || TextUtils.isEmpty(matches)
                ||TextUtils.isEmpty(runs) || TextUtils.isEmpty(wickets))
    }

    private fun getStyleFromRadioButton() {

        if(dataBinding.rbStyleBatsman.isChecked)
            playerStyle="Batsman"
        if(dataBinding.rbStyleBowler.isChecked)
            playerStyle="Bowler"

    }



    // this function is triggered when
    // the Select Image Button is clicked
    private fun imageChooser() {

        // create an instance of the
        // intent of the type image

        val i = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }


        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)

    }


    // this function is triggered when user
    // selects the image from the imageChooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant

            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val uri = data!!.data

                selectedImageBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)

                val stream = ByteArrayOutputStream()
                selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 30, stream)
                byteArray = stream.toByteArray()
                imageSelected=true

                 val image= dataBinding.ivProfilePicture  //setting image in imageview
                    image.setImageBitmap(
                        selectedImageBitmap
                    )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d("onrequest","hello")
            if(requestCode==1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(
                        TAG,
                        "Read External Storage permission is granted  and imageChooser method is called"
                    )
                    imageChooser()
                } else {
                    Log.d(TAG, "Image  permission is still not granted")
                    Toast.makeText(
                        context,
                        "Permissions required to access Gallery",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}