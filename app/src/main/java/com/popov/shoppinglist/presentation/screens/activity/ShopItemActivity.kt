package com.popov.shoppinglist.presentation.screens.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.popov.shoppinglist.R
import com.popov.shoppinglist.databinding.ActivityShopItemBinding
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.presentation.screens.fragments.ShopItemFragment
import com.popov.shoppinglist.presentation.viewmodels.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        launchRightMode()
    }

//    private fun observeViewModel() = with(binding) {
//        viewModel.shopItemLiveData.observe(this@ShopItemActivity) {
//            edtName.setText(it.name)
//            edtCount.setText(it.count.toString())
//        }
//        viewModel.errorInputName.observe(this@ShopItemActivity) {
//            val massage = if (it) {
//                getString(R.string.error_edit_text_name)
//            } else {
//                null
//            }
//            otFieldName.error = massage
//        }
//        viewModel.errorInputCount.observe(this@ShopItemActivity) {
//            val massage = if (it) {
//                getString(R.string.error_edit_text_count)
//            } else {
//                null
//            }
//            otFieldCount.error = massage
//        }
//        viewModel.needToCloseScreen.observe(this@ShopItemActivity) {
//            finish()
//        }
//    }

//    private fun launchEditMod() = with(binding) {
//        viewModel.getShopItem(shopItemId)
//
//        btnSave.setOnClickListener {
//            viewModel.editShopItem(edtName.text?.toString(), edtCount.text?.toString())
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> {
                ShopItemFragment.newInstanceAddItem()
            }
            MODE_EDIT -> {
                ShopItemFragment.newInstanceEditItem(shopItemId)
            }
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction().add(R.id.fcActivityShop, fragment).commit()
    }

//    private fun addChangeTextListener() = with(binding) {
//        Log.d("My", "addChangeTextListener() started")
//        edtName.addTextChangedListener {
//            object : TextWatcher {
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    Log.d("My", "onTextChanged() started")
//                    viewModel.resetErrorInputName()
//                    Log.d("My", "error ${viewModel.errorInputName.value}")
//                }
//
//                override fun afterTextChanged(p0: Editable?) {
//                }
//
//            }
//        }
//        edtCount.addTextChangedListener {
//            object : TextWatcher {
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    viewModel.resetErrorInputCount()
//                }
//
//                override fun afterTextChanged(p0: Editable?) {
//                }
//
//            }
//        }
//    }

//    private fun launchAddMod() = with(binding) {
//        btnSave.setOnClickListener {
//            viewModel.addShopItem(edtName.text?.toString(), edtCount.text?.toString())
//        }
//    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shopItemId is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}