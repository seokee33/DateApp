package com.hama.dateapp.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hama.dateapp.databinding.RvAddlocationItemBinding
import com.hama.dateapp.dto.PlaceInfo
import org.w3c.dom.Text

class RvAddLocationAdapter(context: Context) : RecyclerView.Adapter<RvAddLocationAdapter.Holder>() {

    private lateinit var itemList: ArrayList<PlaceInfo>
    var context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RvAddlocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.binding.btnInputOK.setOnClickListener {
            if (holder.binding.etLocationName.text.isNotEmpty()) {
                if (holder.binding.etAddress.text.isNotEmpty()) {
                    if (holder.binding.rgCategory.isNotEmpty()) {
                        if (holder.binding.rgAtmosphere.isNotEmpty()) {
                            val name: String = holder.binding.etLocationName.text.toString()
//                            val longti:String = holder.bindin
//                            val longti:String = holder.bindin
                            val address:String = holder.binding.etAddress.text.toString()
                            val category:String = holder.binding.rgCategory.isSelected.toString()
                            val atmosphere:String = holder.binding.rgAtmosphere.isSelected.toString()
//                            itemList[position] = PlaceInfo(ho)
                        } else {
                            Toast.makeText(context, "분위기를 선택해주세요", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "주소를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        //이름
        holder.binding.etLocationName.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                itemList[holder.adapterPosition].name = holder.binding.etLocationName.text.toString()
            }

        })
        //주소
        holder.binding.etAddress.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                itemList[holder.adapterPosition].address = holder.binding.etAddress.text.toString()
            }

        })

//        holder.binding.rgCategory.
        //추가 버튼

        //빼기 버튼


        holder.binding.btnAddress.setOnClickListener {

        }
    }



    override fun getItemCount(): Int {
        return itemList.size
    }


    fun addLocation() {
        var temp: PlaceInfo = PlaceInfo("", "", "", "", "", "")
        itemList.add(temp)
    }

    fun removeLocation(position: Int) {
        itemList.removeAt(position)
    }

    fun getItemList(): ArrayList<PlaceInfo> {
        return itemList
    }

    inner class Holder(var binding: RvAddlocationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onAllClick(){

        }
    }

}