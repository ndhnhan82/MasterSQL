package fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.R;

public class RealScenario extends Fragment implements View.OnClickListener {


    private View mRootView;
    private TextView tvRealScenario, tvAnswers;
    private Button btnShowAnswers, btnBack;

    public static RealScenario newInstance() {
        RealScenario fragment = new RealScenario();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate( R.layout.fragment_real_scenario, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {
        String realScenario = "<p><strong>Scenario: Retail Sales and Inventory Management</strong></p>\n" +
                "<p>In a retail business, SQL is commonly used to manage sales data and inventory. Assume you are working for a company that owns multiple retail stores and an online platform. Your task is to analyze and extract useful information from the database to support business decisions.</p>\n" +
                "<p><strong>Database Tables:</strong></p>\n" +
                "<ol><li><p><strong>Products Table:</strong></p><ul><li>Columns: ProductID, ProductName, Category, Price, StockQuantity</li></ul></li><li><p><strong>Sales Table:</strong></p><ul><li>Columns: SaleID, ProductID, SaleDate, Quantity, TotalAmount</li></ul></li><li><p><strong>Customers Table:</strong></p><ul><li>Columns: CustomerID, FirstName, LastName, Email, Phone</li></ul></li></ol>\n" +
                "<p><strong>Question:</strong></p>\n" +
                "<ul>\n" +
                "<li>Find the top 5 products that have generated the highest revenue.</li>\n" +
                "<li>Analyze the monthly sales trend to identify peak sales months.</li>\n" +
                "<li>Identify products that are out of stock.</li>\n" +
                "<li>Retrieve the purchase history for a specific customer.</li>" +
                "</ul>";

        String answers = "<p><strong>Find the top 5 products that have generated the highest revenue.</strong></p>\n" +
                "<p>\n" +
                "SELECT ProductID, ProductName, SUM(TotalAmount) AS Revenue\n" +
                "FROM Sales\n" +
                "JOIN Products ON Sales.ProductID = Products.ProductID\n" +
                "GROUP BY ProductID, ProductName\n" +
                "ORDER BY Revenue DESC\n" +
                "LIMIT 5;\n" +
                "</p>\n" +
                "<p><strong>Analyze the monthly sales trend to identify peak sales months.</strong></p>\n" +
                "<p>\n" +
                "SELECT DATE_FORMAT(SaleDate, '%Y-%m') AS Month, SUM(TotalAmount) AS MonthlyRevenue\n" +
                "FROM Sales\n" +
                "GROUP BY Month\n" +
                "ORDER BY Month;\n" +
                "</p>\n" +
                "<p><strong>Identify products that are out of stock.</strong></p>\n" +
                "<p>\n" +
                "SELECT ProductID, ProductName\n" +
                "FROM Products\n" +
                "WHERE StockQuantity = 0;\n" +
                "</p>\n" +
                "<p><strong>Retrieve the purchase history for a specific customer.</strong></p>\n" +
                "<p>\n" +
                "SELECT CustomerID, FirstName, LastName, ProductName, SaleDate, Quantity, TotalAmount\n" +
                "FROM Customers\n" +
                "JOIN Sales ON Customers.CustomerID = Sales.CustomerID\n" +
                "JOIN Products ON Sales.ProductID = Products.ProductID\n" +
                "WHERE Customers.CustomerID = '12345';\n" +
                "</p>";

        tvRealScenario = (TextView) mRootView.findViewById( R.id.tvRealScenario );
        tvRealScenario.setMovementMethod( new ScrollingMovementMethod() );
        tvRealScenario.setText(android.text.Html.fromHtml(realScenario));
        tvAnswers = (TextView) mRootView.findViewById( R.id.tvAnswers );
//        tvAnswers.setMovementMethod( new ScrollingMovementMethod() );
        tvAnswers.setText(android.text.Html.fromHtml(answers));
        tvAnswers.setVisibility( View.INVISIBLE );
        tvAnswers.setHeight( 0 );

        btnShowAnswers = (Button) mRootView.findViewById( R.id.btnShowAnswers );
        btnShowAnswers.setOnClickListener( this );
        btnBack = (Button) mRootView.findViewById( R.id.btnBack );
        btnBack.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btnShowAnswers){
            tvAnswers.setVisibility( View.VISIBLE );
            tvAnswers.setHeight( 2000 );
        }
        if (id==R.id.btnBack){
            AdminDashboard fragment = new AdminDashboard();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.content_frame, fragment )
                    .addToBackStack( null )
                    .commit();
        }
    }
}