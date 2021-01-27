package com.garrar.driver.app.ui.activity.card;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.data.network.model.Card;
import com.garrar.driver.app.ui.activity.add_card.AddCardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class CardActivity extends BaseActivity implements CardIView {

    @BindView(R.id.cards_rv)
    RecyclerView cardsRv;
    @BindView(R.id.llCardContainer)
    LinearLayout llCardContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.card_container)
    LinearLayout cardContainer;
    @BindView(R.id.img_add_card)
    ImageView img_add_card;
    List<Card> cardsList = new ArrayList<>();
    private CardPresenter<CardActivity> presenter = new CardPresenter<>();
    private final int CREATE_NEW_CARD = 0;
    CreditCardView creditCardView;
    private final int OLD_CARD = 1;
    String cardNumber, cardCVC;
    int cardExpMonth, cardExpYear;
    @Override
    public int getLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.card));
        CardAdapter adapter = new CardAdapter(cardsList);
        cardsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cardsRv.setAdapter(adapter);
        img_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardActivity.this, AddCardActivity.class).putExtra("ischange", "false"));
            }
        });
        //addcard();
        //showCardChangeAlert();
    }

    public void addcard(){
        Intent intent = new Intent(CardActivity.this, CardEditActivity.class);
        startActivityForResult(intent, CREATE_NEW_CARD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if (requestCode == CREATE_NEW_CARD) {
                creditCardView = new CreditCardView(CardActivity.this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(name);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);

                cardContainer.addView(creditCardView);
//                int index = cardContainer.getChildCount() - 1;
                addCardlistener(creditCardView);
            } else {
                CreditCardView creditCardView = (CreditCardView) cardContainer.getChildAt(0);

                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                creditCardView.setCardHolderName(name);
                creditCardView.setCVV(cvv);
            }
        }
    }

    private void addCardlistener(CreditCardView cardView) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardView creditCardView = (CreditCardView) v;
                String cardnumber = creditCardView.getCardNumber();
                String expiry = creditCardView.getExpiry();
                String cardHolderName = creditCardView.getCardHolderName();
                String cvv = creditCardView.getCVV();

                Intent intent = new Intent(CardActivity.this, CardEditActivity.class);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardnumber);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry);
//                intent.putExtra(CreditCardUtils.EXTRA_CARD_CVV, cvv);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_FRONT);
                intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, false);

                // start at the CVV activity to edit it as it is not being passed
                intent.putExtra(CreditCardUtils.EXTRA_ENTRY_START_PAGE, CreditCardUtils.CARD_NUMBER_PAGE);
                startActivityForResult(intent, OLD_CARD);
            }
        });

    }

    @Override
    public void onSuccess(Object card) {
        hideLoading();
        Toasty.success(activity(), getString(R.string.card_deleted_successfully), Toast.LENGTH_SHORT).show();
        showLoading();
        presenter.card();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.card();
    }

    @Override
    public void onSuccess(List<Card> cards) {
        hideLoading();
        cardsList.clear();
        cardsList.addAll(cards);
        cardsRv.setAdapter(new CardAdapter(cardsList));
    }


    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @Override
    public void onSuccessChangeCard(Object card) {
        hideLoading();
        Toasty.success(this, card.toString(), Toast.LENGTH_SHORT).show();
        showLoading();
        presenter.card();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void showCardChangeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.are_sure_you_want_to_change_default_card))
                .setPositiveButton(getResources().getString(R.string.change_card),
                        (dialog, which) -> {
                            startActivity(new Intent(this, AddCardActivity.class).putExtra("ischange", "true"));
                        })
                .setNegativeButton(getResources().getString(R.string.no),
                        (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

        private List<Card> list;

        CardAdapter(List<Card> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Card item = list.get(position);
            holder.card.setText(getString(R.string.card_) + item.getLastFour());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private RelativeLayout itemView;
            private TextView card;
            private TextView changeText;

            MyViewHolder(View view) {
                super(view);
                itemView = view.findViewById(R.id.item_view);
                card = view.findViewById(R.id.card);
                changeText = view.findViewById(R.id.text_change);
                changeText.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Card card = list.get(position);
                switch (view.getId()) {
                    case R.id.text_change:
                        showCardChangeAlert();
                        break;

                    default:
                        break;
                }
            }
        }
    }
}
