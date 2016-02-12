package cmput301.jero1994testcases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {
    public ApplicationTest() {
        super(MainActivity.class);
    }
    //US 05.04.01
    public void testDisplayItems() {
        TestData.testUser = new User();
        TestData.testUser.AddThing("");
        User bidder1 = new User();
        bidder1.MakeBid(TestData.testUser.GetThings().get(0));

        MainActivity main = (MainActivity) getActivity();
        Intent itemListIntent = new Intent(main, ItemListActivity.class);
        main.startActivity(itemListIntent);
        ItemListActivity ila = null;
        while (ila == null) { ila = ItemListActivity.GetInstance(); }
        ViewAsserts.assertOnScreen(main.getWindow().getDecorView(), ila.findViewById(R.id.itemListView));
    }
    //US 05.05.01
    public void testDisplayBids() {
        TestData.testUser = new User();
        TestData.testUser.AddThing("");
        User bidder1 = new User();
        bidder1.MakeBid(TestData.testUser.GetThings().get(0));
        User bidder2 = new User();
        bidder2.MakeBid(TestData.testUser.GetThings().get(0));

        MainActivity main = (MainActivity)getActivity();
        Intent itemListIntent = new Intent(main, ItemListActivity.class);
        main.startActivity(itemListIntent);
        ItemListActivity ila = null;
        while (ila == null) { ila = ItemListActivity.GetInstance(); }

        ListView itemList = (ListView) ila.findViewById(R.id.itemListView);
        assertTrue(itemList.getAdapter().getCount() > 0);
        Intent intent = new Intent(ila, BidListActivity.class);
        intent.putExtra("ItemIndex", 0);
        ila.startActivity(intent);
        BidListActivity bla = null;
        while (bla == null) { bla = BidListActivity.GetInstance(); }
        ViewAsserts.assertOnScreen(main.getWindow().getDecorView(), bla.findViewById(R.id.bidList));

    }
    //US 05.06.01
    public void testAcceptBid() {
        TestData.testUser = new User();
        TestData.testUser.AddThing("");
        User bidder1 = new User();
        bidder1.MakeBid(TestData.testUser.GetThings().get(0));
        User bidder2 = new User();
        bidder2.MakeBid(TestData.testUser.GetThings().get(0));

        Bid bid = TestData.testUser.GetThings().get(0).GetBids().get(0);

        TestData.testUser.AcceptBid(bid);
        assertTrue(bid.GetThing().IsBorrowed());
        assertEquals(bid.GetThing().Borrower(), bid.GetBidder());
        assertEquals(bid.GetThing().GetBids().size(), 0);
    }
    //US 05.07.01
    public void testDeclineBid() {
        TestData.testUser = new User();
        TestData.testUser.AddThing("");
        User bidder1 = new User();
        bidder1.MakeBid(TestData.testUser.GetThings().get(0));
        User bidder2 = new User();
        bidder2.MakeBid(TestData.testUser.GetThings().get(0));

        Bid bid = TestData.testUser.GetThings().get(0).GetBids().get(0);

        TestData.testUser.RejectBid(bid);
        assertFalse(bid.GetThing().IsBorrowed());
        assertEquals(bid.GetThing().Borrower(), null);
        assertEquals(bid.GetThing().GetBids().size(), 1);
    }
}