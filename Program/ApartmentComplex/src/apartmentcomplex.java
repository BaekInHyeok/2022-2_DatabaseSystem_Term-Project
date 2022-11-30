import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class apartmentcomplex {

	static int command1=10;
	static int command2=10;
	static String name;
	static Scanner scanner=new Scanner(System.in);
	
	/*메인 메소드*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*타이틀 출력*/
		System.out.println("아파트 단지 인사 관리 프로그램");
		System.out.println("2022-2 데이터베이스 Term Project. 제작자 : 충북대학교 2020039096 백인혁");
		System.out.println();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://192.168.89.6:4567/ApartmentComplex", "baekinhyeok", "ajskduy6380");
			Statement stmt=con.createStatement();
			PreparedStatement pstmt;
			int ret;
			
			while(command1>0) {
				System.out.println("<수행하려는 업무를 선택하세요>");
				System.out.println("| 종료 : 0 | 직원 : 1 | 부서 : 2 | 아파트 : 3 | 시설 : 4 |");
				System.out.print("입력 : ");
				command1=scanner.nextInt();
				
				System.out.println();
				
				//종료
				if(command1==0) {
					System.out.println("Good Bye!");
					break;
				}
					
				//직원
				else if(command1==1) {
					System.out.println("<명령을 선택하세요>");
					System.out.println("| 직원 목록 : 0 | 직원 검색 : 1 | 직원 추가 : 2 | 직원 삭제 : 3 |");
					System.out.print("입력 : ");
					command2=scanner.nextInt();
					if(command2==0) {
						//직원 목록 출력
						System.out.println("직원 목록입니다.(ID, 이름)");
						ResultSet rs=stmt.executeQuery("SELECT mid, mname FROM Manager");
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2));
						
						System.out.println();
					}
					else if(command2==1) {
						//직원 아이디로 직원 정보 출력
						System.out.print("직원 ID : ");
						int id =scanner.nextInt();
						
						String sql="SELECT * FROM Manager WHERE mid = ";
						
						pstmt=con.prepareStatement(sql+id);
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
						
						System.out.println();
					}
					else if(command2==2) {
						//직원 정보를 입력받아 테이블에 직원 데이터 추가
						System.out.print("직원 아이디 : ");
						int id=scanner.nextInt();
						System.out.print("직원 이름 : ");
						String name=scanner.next();
						System.out.print("나이 : ");
						int age=scanner.nextInt();
						System.out.print("전화번호 : ");
						String tel=scanner.next();
						
						pstmt=con.prepareStatement("INSERT INTO Manager(mid,mname,mage,mtel) VALUES(?,?,?,?)");
						pstmt.setInt(1, id);
						pstmt.setString(2, name);
						pstmt.setInt(3, age);
						pstmt.setString(4, tel);
						ret=pstmt.executeUpdate();
						
						System.out.println();
						
					}
					else if(command2==3){
						//직원 이이디를 입력받아 해당 직원을 테이블에서 삭제
						System.out.print("직원의 아이디를 입력하세요 : ");
						int id =scanner.nextInt();
						
						String sql="DELETE FROM Manager WHERE mid = ";
						
						pstmt=con.prepareStatement(sql+id);
						
						ret=pstmt.executeUpdate();
					}
					else {
						System.out.println("입력 오류");
						
					}
				}
				//부서
				else if(command1==2){
					System.out.println("<실행할 명령을 선택하세요>");
					System.out.println("| 부서 목록 : 0 | 부서별 직원 목록 : 1 | 직원 배정 : 2 | 직원 삭제 : 3 | 업무 배정 : 4 | 업무 배제 : 5 |");
					System.out.print("입력 : ");
					command2=scanner.nextInt();
					
					System.out.println();
					
					if(command2==0) {
						//사무실 목록 출력
						System.out.println("부서 목록(부서 번호, 부서명, 소속 직원 수)");
						ResultSet rs=stmt.executeQuery("SELECT * FROM Office");
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3));
						
						System.out.println();
						
					}
					else if(command2==1) {
						//해당 사무실 소속된 직원들의 아이디와 이름 출력
						System.out.print("부서 번호 : ");
						int ocode=scanner.nextInt();
						
						String sql1="SELECT mid, mname From Manager WHERE mid IN (SELECT mid from WorkIn WHERE ocode = ";
						String sql2=")";
						
						System.out.println("직원 목록(직원번호, 직원명)");
						
						pstmt=con.prepareStatement(sql1+ocode+sql2);
						
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2));
						
						System.out.println();
						
					}
					else if(command2==2) {
						//해당 부서에 직원 추가
						System.out.print("부서 번호 : ");
						int ocode=scanner.nextInt();
						System.out.print("직원 번호 : ");
						int mid=scanner.nextInt();
						
						int count=0;
						
						pstmt=con.prepareStatement("INSERT INTO WorkIn(mid,ocode) VALUES(?,?)");
						pstmt.setInt(1, mid);
						pstmt.setInt(2, ocode);
						
						ret=pstmt.executeUpdate();
						
						
						String sql="SELECT mcount FROM Office WHERE ocode = ";
						
						pstmt=con.prepareStatement(sql+ocode);
						
						ResultSet rs=pstmt.executeQuery();
						
						while(rs.next())
							count=rs.getInt(1);
						count=count+1;
						
						String sql1="UPDATE Office SET mcount = ";
						String sql2=" WHERE ocode = ";
						
						pstmt=con.prepareStatement(sql1+count+sql2+ocode);
						
						ret=pstmt.executeUpdate();
						
						System.out.println();
					}
					else if(command2==3) {
						//직원 소속 부서에서 삭제
						System.out.print("직원 번호 : ");
						int mid=scanner.nextInt();
						int ocode=-1;
						int count=0;
						
						String sql="SELECT ocode FROM WorkIn WHERE mid = ";
						pstmt=con.prepareStatement(sql+mid);
					
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							ocode=rs.getInt(1);
						
						sql="DELETE FROM WorkIn WHERE mid = ";
						
						pstmt=con.prepareStatement(sql+mid);
						
						ret=pstmt.executeUpdate();
						
						sql="SELECT mcount FROM Office WHERE ocode = ";
						pstmt=con.prepareStatement(sql+ocode);
						
						rs=pstmt.executeQuery();
						
						while(rs.next())
							count=rs.getInt(1);
						count=count-1;
						
						String sql1="UPDATE Office SET mcount = ";
						String sql2=" WHERE ocode = ";
						
						pstmt=con.prepareStatement(sql1+count+sql2+ocode);
						
						ret=pstmt.executeUpdate();
						
						System.out.println();
					
					}
					
					else if(command2==4) {
						//직원 아이디와 건물번호를 입력받아 배정
						System.out.print("직원 아이디 입력 : ");
						int mid=scanner.nextInt();
						System.out.print("건물 번호 입력 : ");
						int bcode=scanner.nextInt();
						
						if(bcode <=106) {
							pstmt=con.prepareStatement("INSERT INTO ManageApartment(mid,acode) VALUES(?,?)");
							pstmt.setInt(1, mid);
							pstmt.setInt(2, bcode);
							
							ret=pstmt.executeUpdate();
						}
						else if(bcode > 106) {
							pstmt=con.prepareStatement("INSERT INTO ManageFacility(mid,fcode) VALUES(?,?)");
							pstmt.setInt(1, mid);
							pstmt.setInt(2, bcode);
							
							ret=pstmt.executeUpdate();
						}
						
						System.out.println();
				
					}
					else if(command2==5) {
						//직원 아이디를 입력받아 건물 배정에서 제외
						System.out.print("직원 아이디 입력 : ");
						int mid=scanner.nextInt();
						System.out.print("건물 번호 입력 : ");
						int bcode=scanner.nextInt();
						
						if(bcode <=106) {
							String sql="DELETE FROM ManageApartment WHERE mid = ";
							
							pstmt=con.prepareStatement(sql+mid);
							
							ret=pstmt.executeUpdate();
							
						}
						else if(bcode > 106) {
							String sql="DELETE FROM ManageFacility WHERE mid = ";
							
							pstmt=con.prepareStatement(sql+mid);
							
							ret=pstmt.executeUpdate();
						}
						
						System.out.println();
					}
					
					else {
						System.out.println("입력 오류");
						
					}
					
				}
				//아파트
				else if(command1==3) {
					System.out.println("<실행할 명령을 선택하세요>");
					System.out.println("| 아파트 목록 : 0 | 아파트 근무 직원 목록 : 1 | 대표자 조회 : 2 | 대표자 추가 : 3 | 대표자 삭제 : 4 |");
					System.out.print("입력 : ");
					command2=scanner.nextInt();
					
					if(command2==0) {
						//아파트 목록 출력
						System.out.println("아파트 목록(건물 번호, 아파트명, 층수, 평수, 세대수)");
						ResultSet rs=stmt.executeQuery("SELECT * FROM Apartment");
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getInt(5));
						
						System.out.println();
						
					}
					else if(command2==1) {
						//아파트 코드로 해당 아파트에서 일하는 직원들을 출력한다.
						System.out.print("아파트 번호 입력 : ");
						int acode=scanner.nextInt();
						
						String sql1="SELECT * From Manager WHERE mid IN (SELECT mid from ManageApartment WHERE acode = ";
						String sql2=")";
						
						pstmt=con.prepareStatement(sql1+acode+sql2);
						
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
						
						System.out.println();
					}
					else if(command2==2) {
						//아파트 코드로 대표자를 조회한다
						System.out.print("아파트 번호 입력 : ");
						int acode=scanner.nextInt();
						
						String sql1="SELECT * From President WHERE pname IN (SELECT pname from Represent WHERE acode = ";
						String sql2=")";
						
						pstmt=con.prepareStatement(sql1+acode+sql2);
						
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getString(3));
						
						System.out.println();
						
					}
					else if(command2==3) {
						//아파트에 대표자를 추가한다
						System.out.print("아파트 번호 : ");
						int acode=scanner.nextInt();
						System.out.print("대표자 성명 : ");
						String pname=scanner.next();
						System.out.print("대표자 나이 : ");
						int page=scanner.nextInt();
						System.out.print("대표자 직업 : ");
						String pjob=scanner.next();
						
						pstmt=con.prepareStatement("INSERT INTO President(pname,page,pjob) VALUES(?,?,?)");
						pstmt.setString(1, pname);
						pstmt.setInt(2,page);
						pstmt.setString(3,pjob);
						ret=pstmt.executeUpdate();
						
						pstmt=con.prepareStatement("INSERT INTO Represent(acode,pname) VALUES(?,?)");
						pstmt.setInt(1, acode);
						pstmt.setString(2, pname);
						
						ret=pstmt.executeUpdate();
						
						System.out.println();
						
					}
					else if(command2==4) {
						//아파트에서 대표자를 삭제한다
						System.out.println("@주의! : '이름' 형식으로 입력해야 합니다.");
						System.out.print("대표자명 : ");
						String pname=scanner.next();
						
						String sql="DELETE FROM Represent WHERE pname= ";
						pstmt=con.prepareStatement(sql+pname);
						ret=pstmt.executeUpdate();
						
						sql="DELETE FROM President WHERE pname = ";
						pstmt=con.prepareStatement(sql+pname);
						ret=pstmt.executeUpdate();
						
						System.out.println();
						
					}
					
					else {
						System.out.println("입력 오류");
						
					}
				}
			
				else if(command1==4) {
					System.out.println("<실행할 명령을 선택하세요>");
					System.out.println("| 시설 목록 : 0 | 시설별 근무자 조회 : 1 | 시설 추가 : 2 | 시설 삭제 : 3 |");
					System.out.print("입력 : ");
					command2=scanner.nextInt();
					
					System.out.println();
					
					if(command2==0) {
						//시설 목록 출력
						System.out.println("시설 목록(시설 번호, 시설명, 층수, 면적, 시설 종류");
						ResultSet rs=stmt.executeQuery("SELECT * FROM Facility");
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getString(5));
						
						System.out.println();
					}
					else if(command2==1) {
						//시설을 검색하여 해당 시설에서 근무하는 직원들의 정보 출력
						System.out.print("시설 번호 입력 : ");
						int fcode=scanner.nextInt();
						
						String sql1="SELECT * From Manager WHERE mid IN (SELECT mid from ManageFacility WHERE fcode = ";
						String sql2=")";
						
						pstmt=con.prepareStatement(sql1+fcode+sql2);
						
						ResultSet rs=pstmt.executeQuery();
						while(rs.next())
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
						
						System.out.println();
					}
					else if(command2==2) {
						//시설 추가
						System.out.print("시설번호 : ");
						int fcode=scanner.nextInt();
						System.out.print("시설명 : ");
						String fname=scanner.next();
						System.out.print("층수 : ");
						int ffloor=scanner.nextInt();
						System.out.print("면적 : ");
						int farea=scanner.nextInt();
						System.out.print("종류 : ");
						String ftype=scanner.next();
						
						pstmt=con.prepareStatement("INSERT INTO Facility(fcode,fname,ffloor,farea,buildingtype) VALUES(?,?,?,?,?)");
						pstmt.setInt(1, fcode);
						pstmt.setString(2, fname);
						pstmt.setInt(3, ffloor);
						pstmt.setInt(4, farea);
						pstmt.setString(5, ftype);
						
						ret=pstmt.executeUpdate();
						
						System.out.println();
						
					}
					else if(command2==3) {
						//시설 삭제
						System.out.print("시설번호 : ");
						int fcode=scanner.nextInt();
						
						String sql="DELETE FROM Facility WHERE fcode = ";
						pstmt=con.prepareStatement(sql+fcode);
						ret=pstmt.executeUpdate();
						
						System.out.println();
					}
					
					else {
						System.out.println("입력 오류");
						
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
