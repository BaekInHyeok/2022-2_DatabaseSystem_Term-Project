import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class apartmentcomplex {

	static int command1=-1;;
	static int command2=-1;
	static Scanner scanner=new Scanner(System.in);
	
	/*메인 메소드*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println("아파트 직원 관리 프로그램");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://192.168.89.6:4567/ApartmentComplex", "baekinhyeok", "ajskduy6380");
			Statement stmt=con.createStatement();
			PreparedStatement pstmt;
			int ret;
			
			while(command1>0) {
				System.out.println("<수행하려는 업무를 선택하세요>");
				System.out.println("종료 : 0  직원 : 1  사무실 : 2  아파트 : 3  시설 : 4");
				System.out.print("입력 : ");
				command1=scanner.nextInt();
				
				//종료
				if(command1==0)
					break;
				//직원
				else if(command1==1) {
					System.out.println("실행하실 명령을 선택하세요");
					System.out.println("직원 목록 : 0  직원 검색 : 1  직원 추가 : 2  직원 삭제 : 3  업무 배정 : 4");
					command2=scanner.nextInt();
					if(command2==0) {
						//직원 목록 출력
					}
					else if(command2==1) {
						//직원 이름을 입력받아 해당 직원에 대한 정보 출력 
					}
					else if(command2==2) {
						//직원 정보를 입력받아 테이블에 직원 데이터 추가
					}
					else if(command2==3){
						//직원 이름을 입력받아 해당 직원을 테이블에서 삭제
					}
					else if(command2==4) {
						//직원 코드와 건물 코드를 입력받아 해당 건물에 업무 배치
					}
					else {
						System.out.println("입력 오류");
						break;
					}
				}
				//사무실
				else if(command1==2){
					System.out.println("실행하실 명령을 선택하세요");
					System.out.println("사무실 목록 : 0  직원 배치 : 1  직원 삭제 : 2");
					command2=scanner.nextInt();
					
					if(command2==0) {
						//사무실 목록 출력
					}
					else if(command2==1) {
						//해당 사무실에 직원 배치
					}
					else if(command2==2) {
						//해당 사무실에서 직원 삭제
					}
					
					else {
						System.out.println("입력 오류");
						break;
					}
					
				}
				//아파트
				else if(command1==3) {
					System.out.println("실행하실 명령을 선택하세요");
					System.out.println("아파트 목록 : 0  아파트 검색 : 1  대표자 조회 : 2  대표자 추가 : 3  대표자 삭제 : 4");
					command2=scanner.nextInt();
					
					if(command2==0) {
						//아파트 목록 출력
					}
					else if(command2==1) {
						//아파트 검색하여 해당 아파트 이름 출력
					}
					else if(command2==2) {
						//아파트 코드로 대표자를 조회한다
					}
					else if(command2==3) {
						//아파트에 대표자를 추가한다
					}
					else if(command2==4) {
						//아파트에서 대표자를 삭제한다
					}
					
					else {
						System.out.println("입력 오류");
						break;
					}
				}
			
				else if(command1==4) {
					System.out.println("실행하실 명령을 선택하세요");
					System.out.println("시설 목록 : 0  시설 검색 : 1  시설 추가 : 2  시설 삭제 : 3");
					command2=scanner.nextInt();
					
					if(command2==0) {
						//시설 목록 출력
					}
					else if(command2==1) {
						//시설을 검색하여 해당 시설 정보 출력
						break;
					}
					else if(command2==2) {
						//시설 추가
					}
					else if(command2==3) {
						//시설 삭제
					}
					
					else {
						System.out.println("입력 오류");
						break;
					}
					
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
