package com.example.busbay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.busbay.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private lateinit var athGitImg : ImageView
    private lateinit var athMailImg : ImageView
    private lateinit var athInstaImg : ImageView
    private lateinit var harshGitImg : ImageView
    private lateinit var harshMailImg : ImageView
    private lateinit var harshInstaImg : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val Aboutbinding = FragmentAboutBinding.inflate(inflater , container, false)
            //inflater.inflate(R.layout.fragment_about, container, false)
//        athGitImg = Aboutbinding.gitatharva
        athInstaImg = Aboutbinding.instaatharva
        athMailImg = Aboutbinding.mailatharva
//        harshGitImg= Aboutbinding.githarsh
        harshInstaImg=Aboutbinding.instaharsh
        harshMailImg=Aboutbinding.mailharsh


//        athGitImg.setOnClickListener{
//            val openURL = Intent(Intent.ACTION_VIEW)
//            openURL.data = Uri.parse("https://github.com/atharvabhanage12")
//            startActivity(openURL)
//        }

        Aboutbinding.occupation.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "8074292759")
            startActivity(dialIntent)
        }
        athMailImg.setOnClickListener{

            val email = "atharva.bhanage.20042@iitgoa.ac.in"
            val emailL= email.split(",".toRegex()).toTypedArray()
            val subject = "Mail from Busbay user"
            val body = "Hello Creator"

            val intent=Intent(Intent.ACTION_SENDTO).apply {
                data= Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL,emailL)
                putExtra(Intent.EXTRA_SUBJECT,subject)
                putExtra(Intent.EXTRA_TEXT,body)
            }
            startActivity(intent)
        }

        athInstaImg.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/atharva_1_2/")
            startActivity(openURL)
        }
//        harshGitImg.setOnClickListener{
//            val openURL = Intent(Intent.ACTION_VIEW)
//            openURL.data = Uri.parse("https://github.com/Harshvg101")
//            startActivity(openURL)
//        }
        harshInstaImg.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/harshvg_007/")
            startActivity(openURL)
        }
        harshMailImg.setOnClickListener{
            val email = "harshvardhan.gupta.20033@iitgoa.ac.in"
            val emailL= email.split(",".toRegex()).toTypedArray()
            val subject = "Mail from Busbay user"
            val body = "Hello Creator"

            val intent=Intent(Intent.ACTION_SENDTO).apply {
                data= Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL,emailL)
                putExtra(Intent.EXTRA_SUBJECT,subject)
                putExtra(Intent.EXTRA_TEXT,body)
            }
            startActivity(intent)
        }
        Aboutbinding.occupation2.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "9993557806")
            startActivity(dialIntent)
        }
        // Inflate the layout for this fragment
        return Aboutbinding.root
    }






}