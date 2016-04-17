/*
 * Copyright 2016, Pavel Annin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.annin.store.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.annin.store.R;
import ru.annin.store.presentation.presenter.DetailUnitPresenter;
import ru.annin.store.presentation.ui.view.DetailUnitView;
import ru.annin.store.presentation.ui.viewholder.DetailUnitViewHolder;

/**
 * @author Pavel Annin.
 */
public class DetailUnitActivity extends BaseActivity implements DetailUnitView {

    public static final String EXTRA_UNIT_ID = "ru.annin.store.extra.unit_id";

    @Inject
    DetailUnitPresenter mPresenter;
    @Bind(R.id.main_container)
    View mainView;
    private DetailUnitViewHolder mViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_detail);
        getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        mViewHolder = new DetailUnitViewHolder(mainView);
        mPresenter.setViewHolder(mViewHolder);
        mPresenter.setView(this);
        if (Intent.ACTION_EDIT.equals(getIntent().getAction()) && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(EXTRA_UNIT_ID)) {
            mPresenter.onInitialization(getIntent().getExtras().getString(EXTRA_UNIT_ID));
        } else {
            mPresenter.onInitialization();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mViewHolder.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFinish() {
        finish();
    }
}
