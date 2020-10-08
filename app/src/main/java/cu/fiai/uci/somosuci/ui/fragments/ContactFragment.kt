package cu.fiai.uci.somosuci.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.utils.JsonManager
import cu.fiai.uci.somosuci.utils.MyPermissions
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread

class ContactFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var jsonManager: JsonManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        jsonManager = JsonManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.actualFragment.value = 0
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
        MyPermissions.isCallPermissionsGranted(requireActivity())
    }

    private fun configView() {
        navController = findNavController()

        GlobalScope.launch(Dispatchers.IO) {
            val contact = jsonManager.contactInfo()

            requireContext().runOnUiThread {
                contact.apply {
                    _address.text = address

                    _goFacebook.setOnClickListener {
                        goWebPage(facebook!!)
                    }

                    _goTwitter.setOnClickListener {
                        goWebPage(twitter!!)
                    }

                    _goYoutube.setOnClickListener {
                        goWebPage(youtube!!)
                    }

                    _goLinkedin.setOnClickListener {
                        goWebPage(linkedin!!)
                    }

                    _phone1.text = phone1
                    _phone1.setOnClickListener {
                        if (MyPermissions.isCallPermissionsGranted(requireActivity()))
                            callNum(phone1!!)
                    }

                    _phone2.text = phone2
                    _phone2.setOnClickListener {
                        if (MyPermissions.isCallPermissionsGranted(requireActivity()))
                            callNum(phone2!!)
                    }

                    _email.text = email
                    _email.setOnClickListener {
                        val sendEmail = Intent(Intent.ACTION_SEND)
                        sendEmail.data = Uri.parse("mailto:")
                        sendEmail.type = "message/rfc822"
                        sendEmail.type = "text/html"
                        sendEmail.putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(email)
                        )
                        startActivity(Intent.createChooser(sendEmail, "Enviar Email"))
                    }
                }
            }
        }
    }

    private fun callNum(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data =
            Uri.parse("tel:${phone}")
        try {
            startActivity(intent)
        } catch (ex: Exception) {
        }
    }

    private fun goWebPage(uri: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(uri)
            )
        )
    }
}