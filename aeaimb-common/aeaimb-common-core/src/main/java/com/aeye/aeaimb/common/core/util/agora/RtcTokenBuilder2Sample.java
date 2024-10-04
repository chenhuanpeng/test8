package com.aeye.aeaimb.common.core.util.agora;


import com.aeye.aeaimb.common.core.util.agora.media.RtcTokenBuilder2;
import com.aeye.aeaimb.common.core.util.agora.rtm.RtmTokenBuilder2;
import com.aeye.aeaimb.common.dtos.AgoraTokenParameter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Random;

public class RtcTokenBuilder2Sample {

    static String appId = "12728e80badd4f26bb57f1511c8bd073"; // appid
    static String appCertificate = "db1d3e84d09f43a399ae7837a98c8a3b";  //应用证书
    static String channelName = "7d72365eb983485397e3e3f9d460bdda";  // 频道名称
    static String account = "2082341273";    // 用户名
    static int uid = 2082341273;        // UID
    static int tokenExpirationInSeconds = 60*60*24;
    static int privilegeExpirationInSeconds = 60*60*24;

    public static void main(String[] args) {
       /* RtmTokenBuilder2 token = new RtmTokenBuilder2();
        String result = token.buildToken(appId, appCertificate, userId, expirationInSeconds);
        System.out.println("Rtm Token: " + result);*/

        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result = token.buildTokenWithUid(appId, appCertificate, channelName, uid, RtcTokenBuilder2.Role.ROLE_SUBSCRIBER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.println("Token with uid: " + result);

        result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, RtcTokenBuilder2.Role.ROLE_SUBSCRIBER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.println("Token with account: " + result);

        result = token.buildTokenWithUid(appId, appCertificate, channelName, uid, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.println("Token with uid and privilege: " + result);

        result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.println("Token with account and privilege: " + result);
    }

    public static AgoraTokenParameter getUidTokenUUIDForRTC(){
        int min = 1000;
        int max = 100000;
        int uid = (int) (Math.random() * (max-min+1)) + min ;
        return buildTokenUid(appId, appCertificate, channelName, uid, privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getUidTokenForRTC(){
        return buildTokenUid(appId, appCertificate, channelName, uid, privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getUidTokenForRTC(String channelName,Integer uid){
        return buildTokenUid(appId, appCertificate, channelName, uid, privilegeExpirationInSeconds);
    }

	public static AgoraTokenParameter getUidTokenForRTC(String channelName){
		return buildTokenUid(appId, appCertificate, channelName, getRandomBetween(1000,100000), privilegeExpirationInSeconds);
	}

	public static AgoraTokenParameter getUidTokenForRTCRandomValue(){
		return buildTokenUid(appId, appCertificate, generateString(15), getRandomBetween(1000,100000), privilegeExpirationInSeconds);
	}


	public static AgoraTokenParameter getUidTokenForRTC(String channelName,Integer uid,String appid,String appCertificate){
        return buildTokenUid(appid, appCertificate, channelName, uid, privilegeExpirationInSeconds);
    }


    public static AgoraTokenParameter getAccountTokenForRTC(){
        return buildTokenAccount(appId, appCertificate, channelName, account, privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getAccountTokenForRTC(String channelName,String account){
        return buildTokenAccount(appId, appCertificate, channelName, account, privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getAccountTokenForRTC(String channelName,String account,String appid,String appCertificate){
        return buildTokenAccount(appid, appCertificate, channelName, account, privilegeExpirationInSeconds);
    }



    private static AgoraTokenParameter buildTokenUid(String appid,String appCertificate,String channelName,int uid,int privilegeExpirationInSeconds){
        String result = getInstance().buildTokenWithUid(appid, appCertificate, channelName, uid, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds);

        AgoraTokenParameter agoraTokenParameter =new AgoraTokenParameter();
        agoraTokenParameter.setUid(uid);
        agoraTokenParameter.setToken(result);
//        agoraTokenParameter.setAccount();
        agoraTokenParameter.setAppid(appid);
        agoraTokenParameter.setChannelName(channelName);
		agoraTokenParameter.setType("RTC");
        // 使用上海时区
        ZoneId shanghaiZone = ZoneId.of("Asia/Shanghai");
        // 获取当前上海时间
        LocalDateTime now = LocalDateTime.now(shanghaiZone);
        // 加上5000秒
        LocalDateTime newTime = now.plusSeconds(privilegeExpirationInSeconds);
        // 转换为时间戳 (注意我们仍然需要使用 ZoneOffset 来执行此操作)
        long timestamp = newTime.toEpochSecond(ZoneOffset.UTC); // 因为toEpochSecond期望一个ZoneOffset，我们可以直接用UTC，因为LocalDateTime已经是上海时间了。
        agoraTokenParameter.setExpirationTime(timestamp);
        return agoraTokenParameter;
    }

    private static AgoraTokenParameter buildTokenAccount(String appid,String appCertificate,String channelName,String account,int privilegeExpirationInSeconds){
        String result = getInstance().buildTokenWithUserAccount(appid, appCertificate, channelName, account, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds, privilegeExpirationInSeconds);
        AgoraTokenParameter agoraTokenParameter =new AgoraTokenParameter();
//        agoraTokenParameter.setUid(uid);
        agoraTokenParameter.setToken(result);
        agoraTokenParameter.setAccount(account);
        agoraTokenParameter.setAppid(appid);
        agoraTokenParameter.setChannelName(channelName);
		agoraTokenParameter.setType("RTC");
        // 使用上海时区
        ZoneId shanghaiZone = ZoneId.of("Asia/Shanghai");
        // 获取当前上海时间
        LocalDateTime now = LocalDateTime.now(shanghaiZone);
        // 加上5000秒
        LocalDateTime newTime = now.plusSeconds(privilegeExpirationInSeconds);
        // 转换为时间戳 (注意我们仍然需要使用 ZoneOffset 来执行此操作)
        long timestamp = newTime.toEpochSecond(ZoneOffset.UTC); // 因为toEpochSecond期望一个ZoneOffset，我们可以直接用UTC，因为LocalDateTime已经是上海时间了。
        agoraTokenParameter.setExpirationTime(timestamp);
        return agoraTokenParameter;
    }


    private static AgoraTokenParameter buildTokenUserid(String appid,String appCertificate,String userid,int privilegeExpirationInSeconds){
        String result = getTrm().buildToken(appid, appCertificate, userid, privilegeExpirationInSeconds);
        AgoraTokenParameter agoraTokenParameter =new AgoraTokenParameter();
//        agoraTokenParameter.setUid(uid);
        agoraTokenParameter.setToken(result);
        agoraTokenParameter.setUserid(userid);
        agoraTokenParameter.setAppid(appid);
        agoraTokenParameter.setChannelName(channelName);
		agoraTokenParameter.setType("RTM");
        // 使用上海时区
        ZoneId shanghaiZone = ZoneId.of("Asia/Shanghai");
        // 获取当前上海时间
        LocalDateTime now = LocalDateTime.now(shanghaiZone);
        // 加上5000秒
        LocalDateTime newTime = now.plusSeconds(privilegeExpirationInSeconds);
        // 转换为时间戳 (注意我们仍然需要使用 ZoneOffset 来执行此操作)
        long timestamp = newTime.toEpochSecond(ZoneOffset.UTC); // 因为toEpochSecond期望一个ZoneOffset，我们可以直接用UTC，因为LocalDateTime已经是上海时间了。
        agoraTokenParameter.setExpirationTime(timestamp);
        return agoraTokenParameter;
    }

    public static AgoraTokenParameter getAccountTokenForRTM(String appCertificate,String userid,String appid){
        return buildTokenUserid(appid, appCertificate, userid, privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getAccountTokenForRTM(String appCertificate,String appid){
        return buildTokenUserid(appid, appCertificate, generateString(20), privilegeExpirationInSeconds);
    }

    public static AgoraTokenParameter getAccountTokenUUIDForRTM(){
        return buildTokenUserid(appId, appCertificate, generateString(20), privilegeExpirationInSeconds);
    }

	public static AgoraTokenParameter getAccountTokenUUIDForRTM(String userid){
		return buildTokenUserid(appId, appCertificate, userid, privilegeExpirationInSeconds);
	}

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static String generateString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }


    private RtcTokenBuilder2Sample(){}

    /**
     * 获取实例
     */
    private static RtcTokenBuilder2 getInstance() {
        return StaticSingletonHolder.instance;
    }

    /**
     * 一个私有的静态内部类，用于初始化一个静态final实例
     */
    private static class StaticSingletonHolder {
        private static final RtcTokenBuilder2 instance = new RtcTokenBuilder2();
    }



    /**
     * 获取实例
     */
    private static RtmTokenBuilder2 getTrm() {
        return StaticSingletonHolderTrm.instance;
    }

    /**
     * 一个私有的静态内部类，用于初始化一个静态final实例
     */
    private static class StaticSingletonHolderTrm {
        private static final RtmTokenBuilder2 instance = new RtmTokenBuilder2();
    }



	public static int getRandomBetween(int a, int b) {
		Random rand = new Random();
		// Ensure a is the lower bound and b is the upper bound
		int lower = Math.min(a, b);
		int upper = Math.max(a, b);

		return rand.nextInt(upper - lower + 1) + lower;
	}

}
