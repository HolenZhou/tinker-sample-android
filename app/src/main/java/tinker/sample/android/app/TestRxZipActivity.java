package tinker.sample.android.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tinker.sample.android.R;

/**
 * Created by holenzhou on 2017/6/14.
 */

public class TestRxZipActivity extends AppCompatActivity {

    private TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rx_zip);
        tvText = (TextView) findViewById(R.id.tv_text);
        testZip();
    }

    private void testZip() {
        Observable.zip(getZip1(), getZip2(),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return s + "-----" + s2;
                    }
                })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvText.setText(s);
            }
        });
    }

    private Observable<String> getZip1() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("我是zip1发送的string");
            }
        }).delay(500, TimeUnit.MILLISECONDS);
    }

    private Observable<String> getZip2() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("我是zip2发送的string");
            }
        }).delay(1000, TimeUnit.MILLISECONDS);
    }
}
