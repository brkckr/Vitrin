package com.bullseyedevs.vitrin.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullseyedevs.vitrin.R;
import com.bullseyedevs.vitrin.fragment.ItemFragment;
import com.bullseyedevs.vitrin.model.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brkckr on 28.10.2017.
 *
 * CategoryPagerAdapter represents each page as a Fragment that is persistently
 * kept in the fragment manager as long as the user
 * can return to the page.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter
{
    private List<Solution> solutionList = new ArrayList<>();
    private Context context;

    public CategoryPagerAdapter(FragmentManager manager, Context context, ArrayList<Solution> solutionList)
    {
        super(manager);
        this.solutionList = solutionList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ItemFragment.newInstance(solutionList.get(position));
    }

    @Override
    public int getCount()
    {
        return solutionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return solutionList.get(position).category.name;
    }

    public View getTabView(final int position)
    {
        // Given you have a custom layout in `res/layout/tab_item.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_item, null);

        TextView txtCategoryName = v.findViewById(R.id.txtCategoryName);
        ImageView imgCategoryIcon = v.findViewById(R.id.imgCategoryIcon);

        txtCategoryName.setText(solutionList.get(position).category.name);
        imgCategoryIcon.setImageResource(solutionList.get(position).category.resourceId);

        return v;
    }
}
