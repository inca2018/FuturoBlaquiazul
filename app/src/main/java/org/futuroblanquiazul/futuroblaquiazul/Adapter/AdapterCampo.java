package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterCampo extends RecyclerView.Adapter<AdapterCampo.ViewHolder> {
    private Context context;
    private List<Campo> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    int Puntostotal=0;
    AlertDialog da;

    public AdapterCampo(Context context, List<Campo> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn;

        FrameLayout base;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
                  btn=itemView.findViewById(R.id.card_campos_boton);
                  base=itemView.findViewById(R.id.base_frame);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_campo,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

         switch (my_Data.get(position).getDato()){
             case "P":
                 holder.base.setBackgroundColor(context.getResources().getColor(R.color.red));
                 break;
             case "R":
                 holder.base.setBackgroundColor(context.getResources().getColor(R.color.blue));
                 break;
             case "PG":
                 holder.base.setBackgroundColor(context.getResources().getColor(R.color.morado_bajo));
                 break;
             case "DR":
                 holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                 break;
             case "G":
                 holder.base.setBackgroundColor(context.getResources().getColor(R.color.card));
                 break;
                 default:
                     holder.base.setBackgroundColor(Color.TRANSPARENT);

         }

         holder.btn.getLayoutParams().height=my_Data.get(position).getAltura();

         holder.btn.setHeight(my_Data.get(position).getAltura());


         holder.btn.setText(my_Data.get(position).getDato());
         holder.btn.setTextColor(context.getResources().getColor(R.color.blanco));

         holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout4 = inflater.inflate(R.layout.gestion_campo, null);

                final LinearLayout linear=dialoglayout4.findViewById(R.id.opcion_vaciar_dato);

                final TextView fab1= dialoglayout4.findViewById(R.id.op1);
                final TextView fab2= dialoglayout4.findViewById(R.id.op2);
                final TextView fab3= dialoglayout4.findViewById(R.id.op3);
                final TextView fab4= dialoglayout4.findViewById(R.id.op4);
                final TextView fab5= dialoglayout4.findViewById(R.id.op5);

                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout4);
                da=builder4.show();

                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_Data.get(position).setDato("P");
                        my_Data.get(position).setCant(-1);
                        Puntostotal=Puntostotal-1;
                        notifyDataSetChanged();
                        da.dismiss();

                        System.out.println("Total:"+Puntostotal);

                    }
                });
                fab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_Data.get(position).setDato("R");
                        my_Data.get(position).setCant(1);
                        Puntostotal=Puntostotal+1;
                        notifyDataSetChanged();
                        da.dismiss();

                        System.out.println("Total:"+Puntostotal);
                    }
                });

                fab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_Data.get(position).setDato("PG");
                        my_Data.get(position).setCant(1);
                        Puntostotal=Puntostotal+1;
                        notifyDataSetChanged();
                        da.dismiss();
                        System.out.println("Total:"+Puntostotal);
                    }
                });
                fab4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_Data.get(position).setDato("DR");
                        my_Data.get(position).setCant(1);
                        Puntostotal=Puntostotal+1;
                        notifyDataSetChanged();
                        da.dismiss();
                        System.out.println("Total:"+Puntostotal);
                    }
                });
                fab5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_Data.get(position).setDato("G");
                        my_Data.get(position).setCant(3);
                        notifyDataSetChanged();
                        da.dismiss();
                        System.out.println("Total:"+Puntostotal);
                    }
                });

                linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        my_Data.get(position).setDato("");
                        my_Data.get(position).setCant(0);
                        notifyDataSetChanged();
                        da.dismiss();
                        System.out.println("Total:"+Puntostotal);
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }
    public int getPuntostotal() {
        return Puntostotal;
    }
    public int getP(){
        int sum=0;
        for(int i=0;i<getItemCount();i++){
            if(my_Data.get(i).getDato().equalsIgnoreCase("P")){
                sum=sum+1;
            }else{

            }
        }
        return sum;
    }
    public int getR(){
        int sum=0;
        for(int i=0;i<getItemCount();i++){
            if(my_Data.get(i).getDato().equalsIgnoreCase("R")){
                sum=sum+1;
            }else{

            }

        }
        return sum;
    }
    public int getPG(){
        int sum=0;
        for(int i=0;i<getItemCount();i++){
            if(my_Data.get(i).getDato().equalsIgnoreCase("PG")){
                sum=sum+1;
            }else{

            }

        }
        return sum;
    }
    public int getDR(){
        int sum=0;
        for(int i=0;i<getItemCount();i++){
            if(my_Data.get(i).getDato().equalsIgnoreCase("DR")){
                sum=sum+1;
            }else{

            }

        }
        return sum;
    }
    public int getGoles(){
        int sum=0;
        for(int i=0;i<getItemCount();i++){
            if(my_Data.get(i).getDato().equalsIgnoreCase("G")){
                sum=sum+1;
            }else{

            }
        }
        return sum;
    }

}
