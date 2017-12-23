package com.bullseyedevs.vitrin.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bullseyedevs.vitrin.R;
import com.bullseyedevs.vitrin.adapter.ItemAdapter;
import com.bullseyedevs.vitrin.adapter.SubCategoryAdapter;
import com.bullseyedevs.vitrin.model.Solution;
import com.bullseyedevs.vitrin.model.SubCategory;
import com.bullseyedevs.vitrin.util.TopGridLayoutManager;
import com.bullseyedevs.vitrin.util.TopLinearLayoutManager;

/**
 * Created by brkckr on 28.10.2017.
 */

public class ItemFragment extends Fragment implements SubCategoryAdapter.ISubCategoryAdapterItemCallback
{
    public static final String SOLUTION = "SOLUTION";

    /*
    * Holds the position of the selected sub-category.
    * Change the selected sub-category index
    * by rvItem's scrolling up or down.
    */
    private int selectedSubCategoryPosition = 0;

    /*
    * A solution has all the data needed to populate
    * the RecyclerViews for the sub-categories and items.
    */
    public Solution solution;

    private SubCategoryAdapter subCategoryAdapter;
    public ItemAdapter itemAdapter;

    public RecyclerView rvItem;
    public RecyclerView rvSubCategory;

    @Override
    public void onSubCategoryItemCallback(int position)
    {
        selectedSubCategoryPosition = position;

        for (int index = 0; index < solution.subCategoryList.size(); index++)
        {
            solution.subCategoryList.get(index).isSelected = false;
        }

        solution.subCategoryList.get(position).isSelected = true;
        subCategoryAdapter.notifyDataSetChanged();

        rvSubCategory.smoothScrollToPosition(position);
        rvItem.smoothScrollToPosition(calculateAbsolutePosition(position));
    }

    public static ItemFragment newInstance(Solution solution)
    {
        Bundle args = new Bundle();
        args.putSerializable(SOLUTION, solution);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        solution = (Solution) getArguments().getSerializable(SOLUTION);

        // set items
        rvItem = v.findViewById(R.id.rvItem);
        itemAdapter = new ItemAdapter(getActivity(), solution);
        final TopGridLayoutManager manager = new TopGridLayoutManager(v.getContext(), 2);
        rvItem.setLayoutManager(manager);
        itemAdapter.setLayoutManager(manager);
        itemAdapter.shouldShowHeadersForEmptySections(true);
        itemAdapter.shouldShowFooters(false);
        rvItem.setAdapter(itemAdapter);

        // set sub-categories
        rvSubCategory = v.findViewById(R.id.rvSubCategory);
        rvSubCategory.setLayoutManager(new TopLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL));
        subCategoryAdapter = new SubCategoryAdapter(solution.subCategoryList);
        rvSubCategory.setAdapter(subCategoryAdapter);
        subCategoryAdapter.getItem(0).isSelected = true;
        subCategoryAdapter.subCategoryAdapterItemCallback = this;

        rvItem.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                // find first complete visibile item position
                int firstCompleteleyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();

                if (firstCompleteleyVisibleItemPosition >= 0)
                {
                    // current selected sub-category position
                    int position = getSectionIdByItemPosition(firstCompleteleyVisibleItemPosition);

                    // do these operations
                    // ,f the previously selected and currently selected are different.
                    if (selectedSubCategoryPosition != position)
                    {
                        selectedSubCategoryPosition = position;

                        // mark all sub-categories as unselected
                        for (int index = 0; index < solution.subCategoryList.size(); index++)
                        {
                            solution.subCategoryList.get(index).isSelected = false;
                        }

                        // mark the sub-category at position as selected
                        solution.subCategoryList.get(position).isSelected = true;
                        subCategoryAdapter.notifyDataSetChanged();

                        // smooth scroll to position
                        rvSubCategory.smoothScrollToPosition(position);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return v;
    }

    /*
    * Gets the item position for smooth scroll
    *
    * Want to smooth scroll to the first item of sub-category which is selected
    */
    private int calculateAbsolutePosition(int selectedSubCategoryPosition)
    {
        int absolutePosition = 0;

        for (int i = 0; i < selectedSubCategoryPosition; i++)
        {
            // item header counts 1
            absolutePosition += 1;

            //get the subcategory in the relevant index to use as a key in the map
            SubCategory subCategory = solution.subCategoryList.get(i);

            //get the size of items of each subcategory when sub category is expanded.
            if (subCategory.isExpanded)
            {
                absolutePosition += solution.itemMap.get(subCategory).size();
            }
        }

        return absolutePosition;
    }

    /*
    * Gets section id by first completely visible item position
    *
    * Changing the selected subcategory according to Recyclerview's status
    */
    private int getSectionIdByItemPosition(int firstCompletelyVisibleItemPosition)
    {
        int section = -1;
        int itemSize = 0;

        for (SubCategory subCategory : solution.subCategoryList)
        {
            section++;

            // for header
            itemSize += 1;

            //if it is expanded, get the size of that sub-category's size
            if (subCategory.isExpanded)
            {
                itemSize += solution.itemMap.get(subCategory).size();
            }

            // if itemSize is greater than firstCompletelyVisibleItemPosition,
            // then I have found the section id.
            if (itemSize > firstCompletelyVisibleItemPosition)
            {
                break;
            }
        }

        return section;
    }
}