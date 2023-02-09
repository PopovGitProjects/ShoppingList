package com.popov.shoppinglist.presentation.screens.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.popov.shoppinglist.R
import com.popov.shoppinglist.databinding.FragmentShopItemBinding
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.presentation.viewmodels.ShopItemViewModel

class ShopItemFragment : Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        addChangeTextListener()
        launchRightMode()
        observeViewModel()
        addChangeTextListener()
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("Params screen mode absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT){
            if (!args.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("Params shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, UNKNOWN_ITEM_ID)
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.shopItemLiveData.observe(viewLifecycleOwner) {
            this?.edtName?.setText(it.name)
            this?.edtCount?.setText(it.count.toString())
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.error_edit_text_name)
            } else {
                null
            }
            this?.otFieldName?.error = massage
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.error_edit_text_count)
            } else {
                null
            }
            this?.otFieldCount?.error = massage
        }
        viewModel.needToCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed() // Могут возникнуть проблемы с API < 33
        }
    }

    private fun launchEditMod() = with(binding) {
        viewModel.getShopItem(shopItemId)

        this?.btnSave?.setOnClickListener {
            viewModel.editShopItem(edtName.text?.toString(), edtCount.text?.toString())
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> {
                launchAddMod()
            }
            MODE_EDIT -> {
                launchEditMod()
            }
        }
    }

    private fun addChangeTextListener() = with(binding) {
        Log.d("My", "addChangeTextListener() started") // TODO: не работает!
        this?.edtName?.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("My", "onTextChanged() started")
                    viewModel.resetErrorInputName()
                    Log.d("My", "error:${viewModel.errorInputName.value}")
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            }
        }
        this?.edtCount?.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.resetErrorInputCount()
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            }
        }
    }

    private fun launchAddMod() = with(binding) {
        this?.btnSave?.setOnClickListener {
            viewModel.addShopItem(edtName.text?.toString(), edtCount.text?.toString())
        }
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_ITEM_ID = -1
        private const val MODE_UNKNOWN = ""
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}