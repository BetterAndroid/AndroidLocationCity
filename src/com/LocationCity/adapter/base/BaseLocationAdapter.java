package com.LocationCity.adapter.base;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LocationCity.R;
import com.LocationCity.widget.ContactItemComparator;
import com.LocationCity.widget.ContactItemInterface;
import com.LocationCity.widget.ContactsSectionIndexer;

/**
 * @author wy
 */
public class BaseLocationAdapter extends ArrayAdapter<ContactItemInterface> {

	private int resource; // �洢��Դ����IDΪ1����
	private boolean inSearchMode = false;

	private ContactsSectionIndexer indexer = null;

	protected List<ContactItemInterface> items;
	
	public BaseLocationAdapter(Context context, int resource,
			List<ContactItemInterface> items) {
		super(context, resource, items);
		this.resource = resource;
		this.items = items;
		// need to sort the items array first, then pass it to the indexer
		Collections.sort(items, new ContactItemComparator());
		setIndexer(new ContactsSectionIndexer(items));
	}

	// �õ����в��ֵ�TextView�鿴�Ĳ�����ͼֻ����ʾΪ��һ��
	public TextView getSectionTextView(View rowView) {
		TextView sectionTextView = (TextView) rowView
				.findViewById(R.id.sectionTextView);
		return sectionTextView;
	}

	public void showSectionViewIfFirstItem(View rowView,
			ContactItemInterface item, int position) {
		TextView sectionTextView = getSectionTextView(rowView);
		// ���������ģʽ����ʾsection header
		if (inSearchMode) {
			sectionTextView.setVisibility(View.GONE);
		} else {
			// �����һ�����У�Ȼ����ʾ����
			if (indexer.isFirstItemInSection(position)) {
				String sectionTitle = indexer.getSectionTitle(item
						.getItemForIndex());
				sectionTextView.setText(sectionTitle);
				sectionTextView.setVisibility(View.VISIBLE);

			} else
				sectionTextView.setVisibility(View.GONE);
		}
	}

	// do all the data population for the row here subclass overwrite this to
	// draw more items
	// �Ƿ����е��˿�����Ϊ����������า�Ǵ������������Ŀ
	public void populateDataForRow(View parentView, ContactItemInterface item,
			int position) {
		// Ĭ�������ֻ����ֻ��
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nameView = (TextView) infoView.findViewById(R.id.cityName);
		nameView.setText(item.getItemForIndex());
	}

	// this should be override by subclass if necessary
	// ��Ӧ����ͨ����д���࣬����б�Ҫ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		ContactItemInterface item = getItem(position);

		if (convertView == null) {
			parentView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					inflater);
			vi.inflate(resource, parentView, true);
		} else {
			parentView = (LinearLayout) convertView;
		}

		showSectionViewIfFirstItem(parentView, item, position);
		populateDataForRow(parentView, item, position);

		return parentView;

	}

	public boolean isInSearchMode() {
		return inSearchMode;
	}

	public void setInSearchMode(boolean inSearchMode) {
		this.inSearchMode = inSearchMode;
	}

	public ContactsSectionIndexer getIndexer() {
		return indexer;
	}

	public void setIndexer(ContactsSectionIndexer indexer) {
		this.indexer = indexer;
	}

}
