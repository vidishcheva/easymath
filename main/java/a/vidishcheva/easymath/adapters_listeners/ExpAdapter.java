package a.vidishcheva.easymath.adapters_listeners;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.*;

/**
 * Created by Алёна on 24.03.2016.
 */
public class ExpAdapter {

    final String GROUP_NAME= "groupName";
    final String CHILD_NAME= "name";
    private String[] menuArr;
    private String[] mathArr;
    private String[] geomArr;
    private String[] trigArr;
    ArrayList<Map<String, String>> groupData;
    ArrayList<Map<String, String>> childDataItem;
    ArrayList<ArrayList<Map<String, String>>> childData;
    Map<String, String> m;
    Context context;

    public ExpAdapter(Context context, String[] menuArr, String[] mathArr, String[] geomArr, String[] trigArr) {
        this.context = context;
        this.menuArr = menuArr;
        this.mathArr = mathArr;
        this.geomArr = geomArr;
        this.trigArr = trigArr;
    }
    public SimpleExpandableListAdapter getAdapter() {
        groupData = new ArrayList<>();
        for (String group : menuArr) {
            m = new HashMap<>();
            m.put(GROUP_NAME, group);
            groupData.add(m);
        }

        String groupFrom[] = new String[] {GROUP_NAME};
        int groupTo[] = new int[] {android.R.id.text1};

        childData = new ArrayList<>();
        childDataItem = new ArrayList<>();
        for (String phone : mathArr) {
            m = new HashMap<>();
            m.put(CHILD_NAME, phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);
        childDataItem = new ArrayList<>();
        for (String phone : geomArr) {
            m = new HashMap<>();
            m.put(CHILD_NAME, phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);
        childDataItem = new ArrayList<>();
        for (String phone : trigArr) {
            m = new HashMap<>();
            m.put(CHILD_NAME, phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        String childFrom[] = new String[] {CHILD_NAME};
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);
        return adapter;
    }
}
