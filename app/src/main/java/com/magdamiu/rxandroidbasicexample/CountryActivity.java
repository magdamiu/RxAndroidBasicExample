package com.magdamiu.rxandroidbasicexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class CountryActivity extends MainActivity {

    private Disposable mDisposable;

    @Override
    protected void onStart() {
        super.onStart();

        Observable<String> buttonClickStream = createButtonClickObservable();
        Observable<String> textChangeStream = createTextChangeObservable();

        Observable<String> searchTextObservable = Observable.merge(textChangeStream, buttonClickStream);

        mDisposable = searchTextObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        showProgressBar();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String query) {
                        return mCountrySearchEngine.search(query);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> result) {
                        hideProgressBar();
                        showResult(result);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    // create a method that returns an observable that will emit strings
    private Observable<String> createButtonClickObservable() {

        // create an observable with Observable.create(), and supply it with a new ObservableOnSubscribe
        return Observable.create(new ObservableOnSubscribe<String>() {

            // define an ObservableOnSubscribe by overriding subscribe()
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                // implement the OnClickListener on searchButton
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // on click, call onNext on the emitter and pass it the current text value of the EditText
                        emitter.onNext(countryEditText.getText().toString());
                    }
                });

                // call this when will be disposable
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        // remove the listener
                        searchButton.setOnClickListener(null);
                    }
                });
            }
        });
    }

    private Observable<String> createTextChangeObservable() {
        Observable<String> textChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }
                };

                countryEditText.addTextChangedListener(watcher);

                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        countryEditText.removeTextChangedListener(watcher);
                    }
                });
            }
        });

        return textChangeObservable
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String query) throws Exception {
                        return query.length() >= 2;
                    }
                }).debounce(1000, TimeUnit.MILLISECONDS);  // add this line
    }
}
