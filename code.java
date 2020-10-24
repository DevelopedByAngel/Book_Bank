import java.util.*;
//class for storing global variables
class Global
{
    public static List<book> book=new ArrayList<book>();
    public static List<member> member=new ArrayList<member>();
    public void Start()
    {
    	// adding books to database
    	this.book.add(new book("ooad",2));
    	this.book.add(new book("mpmc",5));
    	this.book.add(new book("toc",8));
    	this.book.add(new book("tht",3));
    	this.book.add(new book("ant",1));
    	this.book.add(new book("mpmc",15));
    	this.book.add(new book("pqt",17));
    	this.book.add(new book("os",0));
    	// pre registered students
    	this.member.add(new member().Pre_registered("2018peccs108","abc",234));
    	this.member.add(new member().Pre_registered("2018peccs109","xyz",897));
    	this.member.add(new member().Pre_registered("2018peccs110","lmn",243));
    	this.member.add(new member().Pre_registered("2018peccs111","uvw",125));
    }  
}
//class for book information
class book
{
	String book;
	int no;
	public book(String book,int no)
	{
		this.book=book;
		this.no=no;
	}
}
//class for new member of unregistered
class member
{
	String roll,name;
	int id;
	Global g=new Global();
	Book_Bank_System bbs=new Book_Bank_System();
	//for pre defined members to store in database
	public member Pre_registered(String roll,String name,int id)
	{
		this.roll = roll;
		this.name = name;
		this.id = id;
		return this;
	}
	//method for registration
	public member Register(String roll,String name)
	{
	    Random rand = new Random(); 
		this.roll = roll;
		this.name = name;
		this.id=rand.nextInt(1000);//to generate id
		bbs.add(this);
		return(this);
	}
}
// class for registerd member inherited from unregistered member
class Registered_member extends member
{
	Global g=new Global();
	Book_Bank_System bbs=new Book_Bank_System();
	//method to login
	public Boolean login(String roll,int id)
	{
		Boolean validated=bbs.validate(roll,id);
		if(validated==false)
		{
			System.out.println("Validation failed. Retry");
		}
		else
		{
			System.out.println("Validated successfully");

		}
		return validated;
	}
	public void select_book(String book)
	{
		System.out.println("You have selected "+book+"\nChecking for availability...");

	}
}
//class for Administrator
class Administrator
{
	Global g=new Global();
	String  book;
	public int Check_available(String book)
	{
		int index=-1;
		for(int i=0;i<g.book.size();i++)
		{
			if(g.book.get(i).book.equals(book) && g.book.get(i).no>0)
			{
				index=i;
				break;
			}
		}
		if (index==-1)
		{
			System.out.println("\n******Book not available******");
			return index;
		}
		else
		{
			System.out.println("\n******Book issued******");
			return index;
		}
	}
	//method to issue book
	public void Issue_Book(int i,String book)
	{
		g.book.set(i,new book(g.book.get(i).book,g.book.get(i).no-1));
	}
}
class Book_Bank_System
{
	Global g=new Global();
	int Working_hours=9;
	public Boolean validate(String roll,int id)
	{
		Boolean validated=false;
		for(int i=0;i<g.member.size();i++)
		{
			if(g.member.get(i).roll.equals(roll) && g.member.get(i).id==id)
			{
				validated=true;
				break;
			}
		}
		return validated;
	}
	public void add(member m)
	{
		g.member.add(m);
	}
}
//main class
class code
{
	public static void main(String args[])
	{
		Scanner scanner=new Scanner(System.in);
		Global g=new Global();
		g.Start();
		Administrator administrator=new Administrator();
		System.out.println("1.Register\n2.Login\n");
		int entry=scanner.nextInt();
		Registered_member a;
		Boolean loggedin=false;
		String next;
		if(entry==1)
		{
			System.out.print("Enter Rollno:");
			String rollno=scanner.next();
			System.out.print("Enter Name:");
			String name=scanner.next();
			member b=new member();
			member m=b.Register(rollno,name);
			a=new Registered_member();
			System.out.println("Your member id is == "+m.id);
			System.out.println("-.-.-This member id must be used for login-.-.-");
			loggedin=a.login(m.roll,m.id);
		}
		else
		{
			System.out.print("Enter Rollno:");
			String rollno=scanner.next();
			System.out.print("Enter Member id:");
			int id=scanner.nextInt();
			a=new Registered_member();
			loggedin=a.login(rollno,id);
		}
		if(loggedin==true)
		{
			System.out.println("Enter 'exit' to exit from process at any time, else enter 'ok' to continue");
			next=scanner.next();
			System.out.println("Select book");
			for (int i=0;i<g.book.size();i++)
			{
				System.out.println((i+1)+") "+g.book.get(i).book+"\n");
			}
			while(next!="exit")
			{
				
				
				String select_book=scanner.next();
				if(select_book.equals("exit"))
					break;
				a.select_book(select_book);
				int book_available=administrator.Check_available(select_book);
				if((book_available!=-1))
				administrator.Issue_Book(book_available,select_book);
				next=select_book;				
				System.out.println("\n\n");
			}
		}
	}
}
