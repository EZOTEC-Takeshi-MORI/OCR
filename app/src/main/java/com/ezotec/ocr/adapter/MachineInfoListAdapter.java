package com.ezotec.ocr.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ezotec.ocr.R;
import com.ezotec.ocr.entity.MachineInfoEntity;

import java.util.List;

/**
 * machine_infoの検索結果を画面に表示するためのadapter
 * BaseAdapterを継承して作成
 */
public class MachineInfoListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<MachineInfoEntity> entityList;

    /**
     * コンストラクタ
     * @param context　元画面のcontext
     */
    public MachineInfoListAdapter(Context context){
        this.context=context;
        this.layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 画面に表示するエンティティのセット
     * @param entity　表示したいエンティティリスト
     */
    public void setMachineInfoEntity(List<MachineInfoEntity> entity){
        this.entityList=entity;
    }

    /**
     * BaseAdapterを継承する際にOverrideする必要のあるメソッド
     * @return　エンティティのサイズ
     */
    @Override
    public int getCount() {
        return entityList.size();
    }

    /**
     * BaseAdapterを継承する際にOverrideする必要のあるメソッド
     * @param position リストのインデックス
     * @return インデックスで選ばれた値
     */
    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    /**
     * BaseAdapterを継承する際にOverrideする必要のあるメソッド
     * @param position リストのインデックス
     * @return インデックスで選ばれたID
     */
    @Override
    public long getItemId(int position) {
        return entityList.get(position).id;
    }

    /**
     *  リストに何を表示するか決める部分
     * @param position　リストのインデックス
     * @param convertView 画面のview
     * @param parent 画面のviewGroup
     * @return convertView
     */
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.adapter_machine_info_list,parent,false);
        ((TextView)convertView.findViewById(R.id.managed_number)).setText(entityList.get(position).getManagedNumber());
        ((TextView)convertView.findViewById(R.id.machine_number)).setText(entityList.get(position).getMachineNumber());
        ((TextView)convertView.findViewById(R.id.machine_name)).setText(entityList.get(position).getMachineName());
        ((TextView)convertView.findViewById(R.id.store_number)).setText(entityList.get(position).getStoreNumber());
        return convertView;
    }
}
