package com.zjht.youoil.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.zjht.youoil.entity.Category;
import com.zjht.youoil.entity.FinanceCard;
import com.zjht.youoil.entity.PosRecord;
import com.zjht.youoil.entity.Product;
import com.zjht.youoil.entity.ProductOrder;
import com.zjht.youoil.manager.PosRecordMng;
import com.zjht.youoil.manager.ProductMng;

public class FTPUtils {
	private static final Logger log=LoggerFactory.getLogger(FTPUtils.class);
	//下载目录
	private static final String dowanloadDir="/home/abc/";  
	//sftp端口
	private static final int FTP_PORT=22;
	//获取并设置SFTP服务器IP地址为172.16.101.210
	private static final String SERVER_ADDRESS = "172.16.101.210";
	private static FTPClient ftp;
	
	
	//FTP上放文件的目录(每10分钟)
    private static final String ftpFileDir="/opt/site/zjhtshop/deploy/ftp/pos/";
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		try {
			FTPUtils ftp=new FTPUtils();
			//ftp.loadSFtp();
			//System.out.println(sftp.connectFtp("121.14.62.204","dsdata","sS8jfsj#"));
			ftp.loadAndDeal("121.14.62.204","dsdata","sS8jfsj#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		long cost=System.currentTimeMillis()-startTime; //共计秒数
		log.info("系统执行【ftp服务器上拿文件，与本地进行同步】监控，时长："+cost+"ms");
	}
	
	/**
	 * 利用JSch包实现SFTP下载、上传文件
	 * @param ip 主机IP
	 * @param user 主机登陆用户名
	 * @param psw  主机登陆密码
	 * @param port 主机ssh2登陆端口，如果取默认值，传-1
	 */
	public static String sshSftp(String ip, String user, String psw ,int port) throws Exception{
	    Session session = null;
	    Channel channel = null;
	    JSch jsch = new JSch();
	    if(port <=0){
	        //连接服务器，采用默认端口
	        session = jsch.getSession(user, ip);
	    }else{
	        //采用指定的端口连接服务器
	        session = jsch.getSession(user, ip ,port);
	    }
	    //如果服务器连接不上，则抛出异常
	    if (session == null) {
	        throw new Exception("session is null");
	    }
	    //设置登陆主机的密码
	    session.setPassword(psw);//设置密码  
	    //设置第一次登陆的时候提示，可选值：(ask | yes | no)
	    session.setConfig("StrictHostKeyChecking", "no");
	    //设置登陆超时时间  
	    session.connect(30000);
	    try {
	        //创建sftp通信通道
	        channel = (Channel) session.openChannel("sftp");
	        channel.connect(1000);
	        ChannelSftp sftp = (ChannelSftp) channel;
	        //进入服务器指定的文件夹
	        sftp.cd(dowanloadDir);
	        //列出服务器指定的文件列表
	        @SuppressWarnings("rawtypes")
			Vector v = sftp.ls("*.txt");
	        for(int i=0;i<v.size();i++){
	            System.out.println(v.get(i));
	        }
	        //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
//	        OutputStream outstream = sftp.put("1.txt");
//	        InputStream instream = new FileInputStream(new File("c:/pos.txt"));
//	         
//	        byte b[] = new byte[1024];
//	        int n;
//	        while ((n = instream.read(b)) != -1) {
//	            outstream.write(b, 0, n);
//	        }
//	        outstream.flush();
//	        outstream.close();
//	        instream.close();
	        
	        //下载
	        OutputStream out = new FileOutputStream(FTPUtils.class.getResource("/").getPath()+"/download.txt");
	        InputStream is = sftp.get("1.txt");
            byte[] buff = new byte[1024 * 2];
            int read;
            if (is != null) {
                System.out.println("Start to read input stream");
                do {
                    read = is.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                    out.flush();
                } while (read >= 0);
                System.out.println("input stream read done.");
            }
    		//InputStream fis = new FileInputStream(url.getPath());
//            List<String> list=FileUtils.readLines(new File(url.getPath()), "UTF-8");
//            for (int i = 1; i < list.size(); i++) {
//				System.out.println(list.get(i));
//			}
            return "download.txt";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        session.disconnect();
	        channel.disconnect();
	    }
	}
	
	public void loadSFtp() throws Exception{
		String filePath=FTPUtils.sshSftp(SERVER_ADDRESS,"root","zjht-123",FTP_PORT);
		URL url = this.getClass().getClassLoader().getResource(filePath);
		File file=new File(url.getFile());
		@SuppressWarnings("unchecked")
		List<String> list=FileUtils.readLines(file);
		if (list!=null&&list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}
	/***
	 * 
	 * @description: <连接ftp服务器，成功则返回，最多连接3次>
	 * @param:
	 * @return:
	 * @throws:
	 */
	public boolean connectFtp(String address,String user,String password){		
		try{
			
			log.info("向服务器" + address + "发起连接请求...");
			System.out.println("向服务器" + address + "发起连接请求...");
			ftp = new FTPClient();
//			FTPClientConfig ftpClientConfig = new FTPClientConfig();  
//	        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());  
//	        ftp.setControlEncoding("GBK");
			int i=3;
			while(i>0){
				/**
				 * 2010/08/24 added
				 * 通过服务器IP地址最多尝试连接三次
				 * 
				 */
				ftp.connect(address);
				ftp.login(user, password);
				boolean isOk = ftp.isConnected();
				log.info(i + " 连接状态 " + isOk);
				if(isOk){
					ftp.enterLocalPassiveMode();
					ftp.setDataTimeout(60 * 1000);
					return true;
				}else{
					i--;
					log.info("尝试向服务器" + SERVER_ADDRESS + "再次发起连接请求...");
				}
				i--;
			}
		}catch(Exception ex){
			log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			return false;
		}
		return false;
	}
	
	public List<ProductOrder> loadAndDeal(String address,String user,String password){
		List<ProductOrder> listData=null;
		try{
			boolean flag=connectFtp(address, user, password);
			//下载文件
			String [] names = ftp.listNames();
			log.info("names"+names);
			if(flag){
				if(names!=null&&names.length>0){
					String dealFileName="YFT"+DateTimeUtils.getDate(-1, "yyyyMMdd")+".txt";
					log.info("dealFileName"+dealFileName);
//					String dealFileName="YFT20141106.txt";
					boolean foundForDeal=false;
					for (String fileName : names) {
						if (!dealFileName.equals(fileName)) {
							continue;
						}
						if (!foundForDeal) {
							listData=new ArrayList<ProductOrder>();
						}
						foundForDeal=true;
						try{
							log.info("开始下载文件" + fileName + "...");
							System.out.println("开始下载文件" + fileName + "...");
							File file = new File(ftpFileDir + fileName);             // 生产机
//							File file = new File("/" + fileName);                    // 本地
							FileOutputStream fos = new FileOutputStream(file);
							ftp.retrieveFile(fileName, fos);
							fos.close();
							log.info(fileName + "文件下载完毕,大小" + file.length() + "bytes"); 
							System.out.println(fileName + "文件下载完毕,大小" + file.length() + "bytes");
							@SuppressWarnings("unchecked")
							List<String> list=FileUtils.readLines(file,"GBK");
							if (list!=null&&list.size()>0) {
								for (int i = 0; i < list.size(); i++) {
									String line=list.get(i);
									if (StringUtils.isBlank(line)) {
										continue;
									}
									String[] arr=line.split("\\|");
									try {
										ProductOrder po=new ProductOrder();
										po.setTradId(arr[0]);//交易id
										po.setTradCardNumber(arr[1]);//卡号
										BigDecimal totalAmount=new BigDecimal(arr[2]);
										//根据总金额预算购买数量(默认为0)
										po.setCount(0);
										po.setUnitCost(totalAmount);//金额
										po.setPayAmount(totalAmount);//支付总金额
										po.setPayStatus(ProductOrder.PayStatus.PAID_SUCCESS.getValue());//支付状态
										po.setPayCode("S");//支付响应代码
										po.setPayIntro("支付成功（pos端）");//支付描述
										po.setPayType(ProductOrder.PAY_TYPE_POS);//支付类型
										po.setSerialNo(arr[3]);//流水号
										po.setReferenceCode(arr[4]);//参考号
										po.setShopCode(arr[5]);//商户号
										po.setCode(arr[6]);//终端号
										po.setBatchNo(arr[7]);//批次号
										if (StringUtils.isNotBlank(arr[8])) {
											po.setTradDate(DateTimeUtils.formatDate(arr[8], "yyyyMMdd"));//交易日期
										}
										if (StringUtils.isNotBlank(arr[9])) {
											po.setTradTime(new Time(DateTimeUtils.formatDate(arr[9], "HHmmss").getTime()));//交易时间
										}
										Date datetime=DateTimeUtils.formatDate(arr[8]+" "+arr[9], "yyyyMMdd HHmmss");
										po.setInTime(datetime);//下单时间
										po.setPayTime(datetime);//支付时间
										po.setCardNumber(arr[10]);//身份证号
										po.setCardHolderMobile(arr[11]);//手机号
										po.setReceiverMobile(arr[11]);//手机号
										po.setReferrer(arr[12]);//推荐人id
										po.setSource(1);//订单来源（0：有油网；1：POS机；2：线下购买）
										po.setName("翼支付电子加油卡");
										po.setCategory(new Category(4L));
										po.setStatus(ProductOrder.OrderStatus.WAIT.getValue());
										po.setOrderNo(DateTimeUtils.genOrderNo(20));
										
										listData.add(po);
									} catch (Exception e) {
										//日志打印
										log.error("封装pos文件数据发生未知异常，异常详情："+e.getMessage());
										if (arr.length==13) {
											//记录处理异常数据记录
											PosRecord bean=new PosRecord();
											//交易id|卡号|金额|流水号|参考号|商户号|终端号|批次号|交易日期|交易时间|身份证号|手机号|推荐人id
											bean.setTradeId(arr[0]);
											bean.setCardNo(arr[1]);
											bean.setAmount(arr[2]);
											bean.setSerialNo(arr[3]);
											bean.setReferenceCode(arr[4]);
											bean.setShopCode(arr[5]);
											bean.setPosNo(arr[6]);
											bean.setBatchNo(arr[7]);
											bean.setTradeDate(arr[8]);
											bean.setTradeTime(arr[9]);
											bean.setIdCardNo(arr[10]);
											bean.setMobile(arr[11]);
											bean.setReferrer(arr[12]);
											bean.setDealStatus(2);
											bean.setDealDesc("处理该记录发生未知异常，异常详情："+e.getMessage());
											posRecordMng.save(bean);
											log.info("下载过程发生异常1:"+e.getMessage()+"，已将异常记录写入了错误日志表!");
											System.out.println("下载过程发生异常:"+e.getMessage()+"，已将异常记录写入了错误日志表!");
										}else{
											System.out.println("分割后列数不对，该行记录未做处理，该行记录："+line);
										}
										continue;
									}
								}
							}
						}catch(Exception ex){
							log.info("下载过程发生异常2:"+ex.getMessage()+"，已将异常文件写入了错误日志表!");
							System.out.println("下载过程发生异常:"+ex.getMessage()+"，已将异常文件写入了错误日志表!");
							return null;
						}
					}
					if (!foundForDeal) {
						log.info("找不到"+dealFileName+"需要处理文件！");
						System.out.println("找不到"+dealFileName+"需要处理文件！");
					}
				}else{
					log.info("下载目录下找不到指定文件，服务器响应代码："+ftp.getReplyCode()+";详细信息："+ftp.getReplyString());
					System.out.println("下载目录下找不到指定文件，服务器响应代码："+ftp.getReplyCode()+";详细信息："+ftp.getReplyString());
				}
				ftp.logout();
				ftp.disconnect();
			}else{
				log.info("没有连接上ftp服务器!");
				System.out.println("没有连接上ftp服务器!");
			}
			return listData;
		}catch(Exception ex){
			System.out.println("连接ftp服务器异常!异常信息："+ex.getMessage());
			return null;
		}
	}
	@Autowired
	private PosRecordMng posRecordMng;
	
	@Autowired
	private ProductMng productMng;
	
	public List<FinanceCard> getListForFinanceCard(String address,String user,String password){
		List<FinanceCard> listData = new ArrayList<FinanceCard>();
		try{
			boolean flag=connectFtp(address, user, password);
			//下载文件
			String [] names = ftp.listNames();
			log.info("names"+names);
			if(flag){
				if(names!=null&&names.length>0){
					String dealFileName="YFT"+DateTimeUtils.getDate(-1, "yyyyMMdd")+".txt";
					log.info("dealFileName"+dealFileName);
//					String dealFileName="YFT20141106.txt";
					boolean foundForDeal=false;
					for (String fileName : names) {
						if (!dealFileName.equals(fileName)) {
							continue;
						}
						if (!foundForDeal) {
							listData=new ArrayList<FinanceCard>();
						}
						foundForDeal=true;
						try{
							log.info("开始下载文件" + fileName + "...");
							System.out.println("开始下载文件" + fileName + "...");
//							File file = new File(ftpFileDir + fileName);             // 生产机
							File file = new File("/" + fileName);                    // 本地
							FileOutputStream fos = new FileOutputStream(file);
							ftp.retrieveFile(fileName, fos);
							fos.close();
							log.info(fileName + "文件下载完毕,大小" + file.length() + "bytes"); 
							System.out.println(fileName + "文件下载完毕,大小" + file.length() + "bytes");
							@SuppressWarnings("unchecked")
							List<String> list=FileUtils.readLines(file,"GBK");
							if (list!=null&&list.size()>0) {
								for (int i = 0; i < list.size(); i++) {
									String line=list.get(i);
									if (StringUtils.isBlank(line)) {
										continue;
									}
									String[] arr=line.split("\\|");
									try {
										FinanceCard po=new FinanceCard();
										po.setTradeId(arr[0]);//交易id
										po.setProduct(productMng.findById(Long.parseLong(arr[1])));//商品ID
										po.setUnionPayNo(arr[2]);//银联卡号
										po.setCardNo(arr[3]);//汇通卡号
										po.setCount(Integer.parseInt(arr[4]));// 购买份数
										BigDecimal totalAmount=new BigDecimal(arr[5]);// 金额
										//根据总金额预算购买数量(默认为0)
										po.setPayMoney(totalAmount);//金额
										po.setSerialNo(arr[6]);// 流水号
										po.setReferenceCode(arr[7]);// 参考号
										po.setShopCode(arr[8]);// 商户号
										po.setBatchNo(arr[9]);// 批次号
										po.setCode(arr[10]);// 终端号
										if (StringUtils.isNotBlank(arr[11])) {
											po.setTradDate(DateTimeUtils.formatDate(arr[11], "yyyyMMdd"));//交易日期
										}
										if (StringUtils.isNotBlank(arr[12])) {
											po.setTradTime(new Time(DateTimeUtils.formatDate(arr[12], "HHmmss").getTime()));//交易时间
										}
										po.setCardNumber(arr[13]);// 身份证号
										po.setMobile(arr[14]);// 手机号
										po.setReferrer(arr[15]);// 推荐人
										listData.add(po);
									} catch (Exception e) {
										//日志打印
										log.error("封装pos文件数据发生未知异常，异常详情："+e.getMessage());
										if (arr.length==16) {
											//记录处理异常数据记录
											PosRecord bean=new PosRecord();
											//交易id|产品ID|银联卡号|汇通卡号|购买份数|金额|流水号|参考号|商户号|终端号|批次号|交易日期|交易时间|身份证号|手机号|推荐人id
											bean.setTradeId(arr[0]);//交易id
											bean.setCardNo(arr[3]);//汇通卡号
											bean.setAmount(arr[6]);//金额
											bean.setSerialNo(arr[7]);// 流水号
											bean.setReferenceCode(arr[8]);// 参考号
											bean.setShopCode(arr[9]);// 商户号
											bean.setBatchNo(arr[10]);// 批次号
											bean.setTradeDate(arr[12]);//交易日期
											bean.setTradeTime(arr[13]);//交易时间
											bean.setIdCardNo(arr[14]);//身份证号
											bean.setMobile(arr[15]);//手机号
											bean.setReferrer(arr[16]);//推荐人id
											bean.setDealStatus(2);
											bean.setDealDesc("处理该记录发生未知异常，异常详情："+e.getMessage());
											posRecordMng.save(bean);
											log.info("下载过程发生异常1:"+e.getMessage()+"，已将异常记录写入了错误日志表!");
											System.out.println("下载过程发生异常:"+e.getMessage()+"，已将异常记录写入了错误日志表!");
										}else{
											System.out.println("分割后列数不对，该行记录未做处理，该行记录："+line);
										}
										continue;
									}
								}
							}
						}catch(Exception ex){
							log.info("下载过程发生异常2:"+ex.getMessage()+"，已将异常文件写入了错误日志表!");
							System.out.println("下载过程发生异常:"+ex.getMessage()+"，已将异常文件写入了错误日志表!");
							return null;
						}
					}
					if (!foundForDeal) {
						log.info("找不到"+dealFileName+"需要处理文件！");
						System.out.println("找不到"+dealFileName+"需要处理文件！");
					}
				}else{
					log.info("下载目录下找不到指定文件，服务器响应代码："+ftp.getReplyCode()+";详细信息："+ftp.getReplyString());
					System.out.println("下载目录下找不到指定文件，服务器响应代码："+ftp.getReplyCode()+";详细信息："+ftp.getReplyString());
				}
				ftp.logout();
				ftp.disconnect();
			}else{
				log.info("没有连接上ftp服务器!");
				System.out.println("没有连接上ftp服务器!");
			}
			return listData;
		}catch(Exception ex){
			System.out.println("连接ftp服务器异常!异常信息："+ex.getMessage());
			return null;
		}
	}
}
