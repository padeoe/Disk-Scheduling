import java.util.Arrays;
/**
 * 《操作系统教程（第五版）》P294习题
 * @author Kangkang
 *
 */
public class Main
{
	public static void main(String[] args)
	{
		int init7=143,init6=11;
		int s7[]={86,147,91,177,94,150,102,175,130};
		int s6[]={1,36,16,34,9,12};
		System.out.println("6.");
		System.out.println("(1)FIFO:"+fcfs(init6,s6));
		System.out.println("(2)SST:"+sst(init6,s6));
		System.out.println("(3)SCAN:"+scan(init6,s6,true));
		System.out.println("7.");
		System.out.println("(1)FCFS:"+fcfs(init7,s7));
		System.out.println("(2)SST:"+sst(init7,s7));
		System.out.println("(3)SCAN:"+scan(init7,s7,true));

	}
	/**
	 * 先来先服务算法（FCFS）
	 * @param init 磁头的初始位置
	 * @param s 磁道请求序列
	 * @return 穿越的总磁道数的计算式与结果
	 */
	public static String fcfs(int init,int s[]){
		StringBuilder formula=new StringBuilder();
		int sum=Math.abs(init-s[0]);
		formula.append("("+Math.max(init,s[0])+"-"+Math.min(init,s[0])+")");
		for(int i=1;i<s.length;i++){
			sum+=Math.abs(s[i]-s[i-1]);
			formula.append("+"+"("+Math.max(s[i],s[i-1])+"-"+Math.min(s[i],s[i-1])+")");
		}
		return formula.toString()+"="+sum;
	}
	/**
	 * 最短查找时间优先算法（SST）
	 * @param init 磁头的初始位置
	 * @param ss 磁道请求序列
	 * @return 穿越的总磁道数的计算式与结果
	 */
	public static String sst(int init,int ss[]){
		int s[]=Arrays.copyOf(ss, ss.length);
		Arrays.sort(s);
		int i;
		for(i=0;i<s.length;i++){
			if(init<s[i]){
				break;
			}
		}
		if(i==0)
			return s[s.length-1]+"-"+init+"="+(s[s.length-1]-init);
		if(i==s.length)
			return init+"-"+s[0]+"="+(init-s[0]);
		int sum=0,current=init;
		StringBuilder formula=new StringBuilder();
		if(Math.abs(s[i]-init)<Math.abs(s[i-1]-init)){
			formula.append("("+Math.max(s[i], init)+"-"+Math.min(s[i],init)+")");
			current=i;
		//	s[i]=-1;
			
			sum+=Math.abs(s[i]-init);
		}
		else{
			formula.append("("+Math.max(s[i-1], init)+"-"+Math.min(s[i-1],init)+")");
			current=i-1;
		//	s[i-1]=-1;
			
			sum+=Math.abs(s[i-1]-init);
		}
		for(int k=0;k<s.length;k++){
			int left=-1,right=-1;
			for(int p=current-1;p>=0;p--){
				if(s[p]!=-1){
					left=p;
					break;
				}
			}
			for(int p=current+1;p<s.length;p++){
				if(s[p]!=-1){
					right=p;
					break;
				}
			}
			int leftDistance=-1,rightDistance=-1;
			if(left!=-1){
				leftDistance=Math.abs(s[current]-s[left]);
			}
			if(right!=-1){
				rightDistance=Math.abs(s[right]-s[current]);
			}
			if(leftDistance!=-1&&rightDistance!=-1&&leftDistance<rightDistance){
				formula.append("+("+s[current]+"-"+s[left]+")");
				s[current]=-1;
				current=left;
				sum+=leftDistance;
			}
			if(leftDistance!=-1&&rightDistance!=-1&&leftDistance>rightDistance){
				formula.append("+("+s[right]+"-"+s[current]+")");
				s[current]=-1;
				current=right;
				sum+=rightDistance;
			}
			if(leftDistance!=-1&&rightDistance==-1){
				formula.append("+("+s[current]+"-"+s[left]+")");
				s[current]=-1;
				current=left;
				sum+=leftDistance;
			}
			if(leftDistance==-1&&rightDistance!=-1){
				formula.append("+("+s[right]+"-"+s[current]+")");
				s[current]=-1;
				current=right;
				sum+=rightDistance;
			}
		}
		return formula.toString()+"="+sum;
	}
	/**
	 * 扫描算法（SST）
	 * @param init 磁头的初始位置
	 * @param ss 磁道请求序列
	 * @param up 移动臂方向是否升序
	 * @return 穿越的总磁道数的计算式与结果
	 */
	public static String scan(int init,int ss[],boolean up){
		int s[]=Arrays.copyOf(ss, ss.length);
		Arrays.sort(s);
		if(up){
			return "("+s[s.length-1]+"-"+init+")+("+s[s.length-1]+"-"+s[0]+")="+(s[s.length-1]-init+s[s.length-1]-s[0]);
		}
		else{
			return "("+init+"-"+s[0]+")+("+s[s.length-1]+"-"+s[0]+")";
		}
	}
}
