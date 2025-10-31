package com.sm.graduation.root.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.graduation.Exception.ServiceException;
import com.sm.graduation.accident.dao.AccidentMapper;
import com.sm.graduation.accident.pojo.AccidentPojo;
import com.sm.graduation.accident.service.AccidentService;
import com.sm.graduation.admin.pojo.AdminInfo;
import com.sm.graduation.admin.service.AdminInfoService;
import com.sm.graduation.checkin.pojo.CheckIn;
import com.sm.graduation.checkin.service.CheckInService;
import com.sm.graduation.common.loginpojo.LoginPojo;
import com.sm.graduation.common.result.AjaxResult;
import com.sm.graduation.dormitory.pojo.DormitoryAllocation;
import com.sm.graduation.dormitory.service.DormitoryAllocationService;
import com.sm.graduation.food.pojo.MonthlyCatering;
import com.sm.graduation.food.service.MonthlyCateringService;
import com.sm.graduation.health.pojo.HealthRecords;
import com.sm.graduation.health.service.HealthRecordsService;
import com.sm.graduation.high.pojo.HighRisk;
import com.sm.graduation.high.service.HighRiskService;
import com.sm.graduation.medication.pojo.Medication;
import com.sm.graduation.medication.service.MedicationService;
import com.sm.graduation.nurse.pojo.Nursing;
import com.sm.graduation.nurse.service.NursingService;
import com.sm.graduation.older.pojo.OlderInfo;
import com.sm.graduation.older.service.OlderInfoService;
import com.sm.graduation.out.pojo.GoOut;
import com.sm.graduation.out.service.GoOutService;
import com.sm.graduation.root.pojo.RootInfo;
import com.sm.graduation.tubiao.dao.TubiaoMapper;
import com.sm.graduation.tubiao.pojo.ToTwo;
import com.sm.graduation.tubiao.pojo.Tubiao;
import com.sm.graduation.tubiao.service.TubiaoService;
import com.sm.graduation.userInformation.dao.UserInformationMapper;
import com.sm.graduation.userInformation.pojo.UserInfoAddReq;
import com.sm.graduation.userInformation.pojo.UserInfomation;
import com.sm.graduation.userInformation.pojo.UserInformationReq;
import com.sm.graduation.userInformation.pojo.UserReq;
import com.sm.graduation.userInformation.service.UserInformationService;
import com.sm.graduation.usr.pojo.UsrInfo;
import com.sm.graduation.usr.service.UsrInfoService;
import com.sm.graduation.visitor.pojo.Visitor;
import com.sm.graduation.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sm.graduation.root.service.RootInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sm.graduation.common.result.AjaxResult.*;

/**
 *   Controller层用来做前后端连接的
 */

@RestController
@RequestMapping("/root")
public class RootInfoController {

    //  @Autowired：将一个类注入到另一个类中使用的注释
    @Autowired
    private RootInfoService rootInfoService;

    @Autowired
    private AdminInfoService adminInfoService;

    @Autowired
    private UsrInfoService usrInfoService;

    @Autowired
    private HealthRecordsService healthRecordsService;

    @Autowired
    private HighRiskService highRiskService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private MonthlyCateringService monthlyCateringService;

    @Autowired
    private GoOutService goOutService;

    @Autowired
    private OlderInfoService olderInfoService;

    @Autowired
    private DormitoryAllocationService dormitoryAllocationService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private NursingService nursingService;

    private String olderName;
    private boolean login;

    //  @RequestMapping即使类的注解也是方法的注解
    // 方法的写法： public 返回值类型  方法名(参数){}
    // HttpServletRequest: 主要是前端到后端的请求参数如：请求头、请求方法、请求参数
    // HttpSession: 用于存储前端传过来的参数
    // LoginPojo: 前端将用户输入的信息存放到LoginPojo这个类里
    @RequestMapping("/loginIn")
    public AjaxResult loginIn(HttpServletRequest request,HttpSession session,LoginPojo loginPojo){
        // 先判断用户输入的验证码和生成的验证码是否一致
        // equalsIgnoreCase与equals的区别： equalsIgnoreCase与equals的区别不比较大小写，一般用于验证码，
        // equals：比较大小写
            if (loginPojo.getCaptcha().equalsIgnoreCase(String.valueOf(request.getSession().getAttribute("captCode")))){
                // 判断一下登录人的权限
                if (loginPojo.getPower() == 0 || "0".equals(loginPojo.getPower())){
                    // 首先判断该用户是否存在
                    RootInfo rootInfo = rootInfoService.seleByName(loginPojo.getUsername());
                    if (rootInfo == null){
                        throw new ServiceException("该用户不存在，请重新输入");
                    }
                    // 如果有该用户，判断改用的密码是否正确
                    RootInfo rootInfo1 = new RootInfo();
                    // set方法用于放值，get方法用于取值
                    rootInfo1.setName(loginPojo.getUsername());
                    rootInfo1.setPwd(loginPojo.getPassword());
                    int i = rootInfoService.seleByNamePwd(rootInfo1);
                    // 如果密码错误的话，给个提示
                    if (i == 0){
                        throw new ServiceException("密码错误，请重新输入");
                    }
                    session.setAttribute("username",loginPojo.getUsername());
                    return success(0,"登录成功");
                }
                //  判断admin用户
                if (loginPojo.getPower() == 1 || "1".equals(loginPojo.getPower())){
                    // 看一下登录用户是否存在
                    AdminInfo adminInfo = new AdminInfo();
                    adminInfo.setAdminLogin(loginPojo.getUsername());
                    // 查看用户是否存在
                    AdminInfo adminInfo1 = adminInfoService.sltName(adminInfo);
                    if (adminInfo1 != null){
                        // 判断该用户的密码是否正确
                        adminInfo.setAdminPwd(loginPojo.getPassword());
                        boolean b = adminInfoService.loginIn(adminInfo);
                        if (b){
                            session.setAttribute("username",adminInfo1.getAdminName());
                            return success(1,"登录成功");
                        }else {
                            return error("密码不正确，请重新输入");
                        }
                    }else {
                        return error("该用户不存在，请重新输入");
                    }
                }


            }
                return error("验证码错误，请重新输入");
    }

    @Autowired
    private AccidentService accidentService;
    //  事故记录页面查询
    // @RequestParam: 参数的注解 1、defaultValue：默认值; 2.value：取别名
    @RequestMapping("/accident")
    public AjaxResult accident(@RequestParam(defaultValue = "1",value = "page") Integer pageNum,Integer limit,String acc){
        List<AccidentPojo> accidentPojos = accidentService.selectByAll(pageNum,limit,acc);
            if (accidentPojos != null && accidentPojos.size() > 0){
            return successData(accidentPojos.get(0).getTotal(),accidentPojos);
        }
        return error("暂无数据");
    }


    // 事故的添加
    @RequestMapping("/addAcc")
    public AjaxResult addAcc(AccidentPojo accidentPojo){
        // 到service层中做业务逻辑处理
        int i = accidentService.insertByAll(accidentPojo);
        if (i>0){
            return success("添加成功");
        }
        return error("添加失败");
    }

    // 编辑
    @RequestMapping("/modifyAcc")
    public AjaxResult modifyAcc(AccidentPojo accidentPojo){
        int i = accidentService.updateById(accidentPojo);
        if (i >0){
            return success("编辑成功");
        }
        return error("编辑失败");
    }

    @Autowired
    private AccidentMapper accidentMapper;

    // 删除
    @RequestMapping("/batchDelAcc")
    public AjaxResult batchDelAcc(String listStr){
        if (!"".equals(listStr)){
            // 将传来的string字符串转化成数组
            String[] split = listStr.split(",");
            // [,37,36]
            int i =0;
            // 遍历该数组，取到对应的id
            for (String s :split){
                // 判断id是否为空
                if (!"".equals(s)){
                    // mapper
                     int i1 = accidentMapper.deleteById(Integer.parseInt(s));
                     if (i1 >0){
                         i++;
                     }
                }
            }
            if (i > 0){
                return success();
            }
        }
        return error("传参不能为空");
    }

//图表
@Autowired
private TubiaoService tubiaoService;

    //    图表1展示
    @RequestMapping("/tuOne")
    public Tubiao tuOne(){
        Tubiao tubiao = tubiaoService.tuOne();
        return tubiao;
    }
    @Autowired
    private TubiaoMapper tubiaoMapper;
//    图二的展示
    @RequestMapping("/tuTwo")
    public List<ToTwo> tuTwo(){
//        到mapper层做sql语句处理
        List<ToTwo> toTwos = tubiaoMapper.selectByTwo();
        return toTwos;
    }




    @Autowired
    private VisitorService visitorService;

    @RequestMapping("/visitor")
    public AjaxResult visitor(Integer page,Integer limit,String name){
        List<Visitor> visitors = visitorService.selectByAll(page, limit, name);
        if (visitors == null){
            return error("暂无数据");
        }
        if (visitors.size() > 0 && visitors != null){
            return successData(visitors.get(0).getTotal(),visitors);
        }
        return null;
    }

    // 访客添加
    @RequestMapping("/addVis")
    public AjaxResult addVis(Visitor visitor){
        int i = visitorService.insertByAll(visitor);
        if (i>0){
            return success("添加成功");
        }
        return error("添加失败");
    }

    @Autowired
    private UserInformationService userInformationService;
//出面记录页面查询
    @RequestMapping("discharge")
    public AjaxResult discharge(Integer page, Integer limit, UserInformationReq req){
        //到service做业务逻辑处理
        List<UserInfomation> userInfomations=userInformationService.selectByAll(page, limit, req);
        if (userInfomations.size() >0 && userInfomations !=null){
            return successData(userInfomations.get(0).getTotal(),userInfomations);
        }
        return error("数据为空");
    }

    @Autowired
    private UserInformationMapper userInformationMapper;
// 查询用户信息
    @RequestMapping("/selectBYUser")
    public List<UserReq> selectBYUser(){
//        调用mapper
        List<UserReq> userReqs = userInformationMapper.selectByUser();
        return userReqs;
    }
//    查询药品信息
    @RequestMapping("/selectBYmedication")
    public List<UserReq> selectBYmedication(){
        List<UserReq> userReqs = userInformationMapper.selectBYmedication();
        return userReqs;
    }
//
    @RequestMapping("/addis")
    public AjaxResult Addis(UserInfoAddReq req)
    {
//        到service层做业务逻辑处理
        int i = userInformationService.addUserInfo(req);
        if (i >0){
            return success("添加成功");
        }else {
            return error("添加失败");
        }
    }

//修改
    @RequestMapping("/modifyis")
    public AjaxResult modifyis(UserInfoAddReq req){
//        到service中做业务处理
        int i = userInformationService.updateById(req);
        if (i>0){
            return success("修改成功");
        }
        else {
            return error("修改失败");
        }
    }
//    删除
    @RequestMapping("/deleteById")
    public AjaxResult deleteById(String listStr){
//        判断传来的值是否为空
        if (!"".equals(listStr)){
//            将传入的String类型的值转换为 数组[]
            String[] split = listStr.split(",");
            int i=0;
            for (String s:split){
                if (!"".equals(s)){
//                    到mapper中做sql语句处理
                    int i1 = userInformationMapper.deleteById(Integer.parseInt(s));
                    if (i1 >0){
                        i++;
                    }
                }
            }
            if (i >0){
                return success("删除成功");
            }
        }
        return error("传参不能为空");
    }


    /** admin  ---  List */
    @RequestMapping("/adminList")
    public AjaxResult adminList(@RequestParam(defaultValue = "1" , value = "page") Integer pageNum,
                                @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                                @RequestParam(defaultValue = ""  , value = "adminName") String user
                                ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (user == null || user.equals("")) {
            map.put("user","");
            List<AdminInfo> admins = adminInfoService.listAll(map);
            PageInfo<AdminInfo> page = new PageInfo<>(admins);
            return successData(page.getTotal(), admins);
        }
            map.put("user",user);
            List<AdminInfo> admins = adminInfoService.listAll(map);
            PageInfo<AdminInfo> page = new PageInfo<>(admins);
            return successData(page.getTotal(), admins);
    }


    /** usr --- List */
    @RequestMapping("/userList")
    public AjaxResult userList(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                               @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                               @RequestParam(defaultValue = "" , value = "usrName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<UsrInfo> usrInfos = usrInfoService.listAll(map);
            PageInfo<UsrInfo> page = new PageInfo<>(usrInfos);
            return successData(page.getTotal(), usrInfos);
        }
        map.put("usr",usr);
        List<UsrInfo> usrInfos = usrInfoService.listAll(map);
        PageInfo<UsrInfo> page = new PageInfo<>(usrInfos);
        return successData(page.getTotal(), usrInfos);
    }


    /** 健康档案 */
    @RequestMapping("/healthRisk")
    public AjaxResult healthRisk(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                                 @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                                 @RequestParam(defaultValue = "" , value = "olderName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<HealthRecords> healthRecords = healthRecordsService.listAll(map);
            PageInfo<HealthRecords> page = new PageInfo<>(healthRecords);
            return successData(page.getTotal(), healthRecords);
        }
        map.put("usr",usr);
        List<HealthRecords> healthRecords = healthRecordsService.listAll(map);
        PageInfo<HealthRecords> page = new PageInfo<>(healthRecords);
        return successData(page.getTotal(), healthRecords);
    }
    @GetMapping("/batchDelHealthdetail")
    public Map<String, Object> batchDelHealthdetail() {
        Map<String, Object> selectdetail = healthRecordsService.selectdetail();

        return selectdetail;
    }

    /** 高危存档 */
    @RequestMapping("/highRisk")
    public AjaxResult highRisk(@RequestParam(defaultValue = "1",  value = "page") Integer pageNum,
                               @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                               @RequestParam(defaultValue = ""  , value = "olderName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<HighRisk> highRisks = highRiskService.listAll(map);
            PageInfo<HighRisk> page = new PageInfo<>(highRisks);
            return successData(page.getTotal(), highRisks);
        }
        map.put("usr",usr);
        List<HighRisk> highRisks = highRiskService.listAll(map);
        PageInfo<HighRisk> page = new PageInfo<>(highRisks);
        return successData(page.getTotal(), highRisks);
    }


    /** 药品管理 */
    @RequestMapping("/medication")
    public AjaxResult medication(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                                 @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                                 @RequestParam(defaultValue = "" , value = "medication") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<Medication> medications = medicationService.listAll(map);
            PageInfo<Medication> page = new PageInfo<>(medications);
            return successData(page.getTotal(), medications);
        }
        map.put("usr",usr);
        List<Medication> medications = medicationService.listAll(map);
        PageInfo<Medication> page = new PageInfo<>(medications);
        return successData(page.getTotal(), medications);
    }


    /** 每月餐饮 */
    @RequestMapping("/catering")
    public AjaxResult catering(@RequestParam(defaultValue = "1",  value = "page") Integer pageNum,
                               @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                               @RequestParam(defaultValue = ""  , value = "monTime") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<MonthlyCatering> monthlyCaterings = monthlyCateringService.listAll(map);
            PageInfo<MonthlyCatering> page = new PageInfo<>(monthlyCaterings);
            return successData(page.getTotal(), monthlyCaterings);
        }
        map.put("usr",usr);
        List<MonthlyCatering> monthlyCaterings = monthlyCateringService.listAll(map);
        PageInfo<MonthlyCatering> page = new PageInfo<>(monthlyCaterings);
        return successData(page.getTotal(), monthlyCaterings);
    }


    /** 外出报备 */
    @RequestMapping("/goOut")
    public AjaxResult goOut(@RequestParam(defaultValue = "1",  value = "page") Integer pageNum,
                            @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                            @RequestParam(defaultValue = ""  , value = "olderName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<GoOut> goOuts = goOutService.listAll(map);
            PageInfo<GoOut> page = new PageInfo<>(goOuts);
            return successData(page.getTotal(), goOuts);
        }
        map.put("usr",usr);
        List<GoOut> goOuts = goOutService.listAll(map);
        PageInfo<GoOut> page = new PageInfo<>(goOuts);
        return successData(page.getTotal(), goOuts);
    }

    @RequestMapping("/goOutUsr")
    public AjaxResult goOutUsr(@RequestParam(defaultValue = "1",  value = "page") Integer pageNum,
                            @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                            @RequestParam(defaultValue = ""  , value = "olderName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("olderName",olderName);
            List<GoOut> goOuts = goOutService.go_listAll(map);
            PageInfo<GoOut> page = new PageInfo<>(goOuts);
            return successData(page.getTotal(), goOuts);
        }
        map.put("usr",usr);
        List<GoOut> goOuts = goOutService.listAll(map);
        PageInfo<GoOut> page = new PageInfo<>(goOuts);
        return successData(page.getTotal(), goOuts);
    }


    /** 入住登记 */
    @RequestMapping("/register")
    public AjaxResult register(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                               @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                               @RequestParam(defaultValue = ""  , value = "olderName") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<OlderInfo> olderInfos = olderInfoService.listAll(map);
            PageInfo<OlderInfo> page = new PageInfo<>(olderInfos);
            return successData(page.getTotal(), olderInfos);
        }
        map.put("usr",usr);
        List<OlderInfo> olderInfos = olderInfoService.listAll(map);
        PageInfo<OlderInfo> page = new PageInfo<>(olderInfos);
        return successData(page.getTotal(), olderInfos);
    }


    /** 寝室分配 */
    @RequestMapping("/dormitory")
    public AjaxResult dormitory(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                                @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                                @RequestParam(defaultValue = ""  , value = "dormitory") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<DormitoryAllocation> dormitoryAllocations = dormitoryAllocationService.listAll(map);
            PageInfo<DormitoryAllocation> page = new PageInfo<>(dormitoryAllocations);
            return successData(page.getTotal(), dormitoryAllocations);
        }
        map.put("usr",usr);
        List<DormitoryAllocation> dormitoryAllocations = dormitoryAllocationService.listAll(map);
        PageInfo<DormitoryAllocation> page = new PageInfo<>(dormitoryAllocations);
        return successData(page.getTotal(), dormitoryAllocations);
    }


    /** 入住费用 */
    @RequestMapping("/checkIn")
    public AjaxResult checkIn(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                              @RequestParam(defaultValue = ""  , value = "year") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<CheckIn> checkIns = checkInService.listAll(map);
            PageInfo<CheckIn> page = new PageInfo<>(checkIns);
            return successData(page.getTotal(), checkIns);
        }
        map.put("usr",usr);
        List<CheckIn> checkIns = checkInService.listAll(map);
        PageInfo<CheckIn> page = new PageInfo<>(checkIns);
        return successData(page.getTotal(), checkIns);
    }
    /** 护理费用 */
    @RequestMapping("/nursing")
    public AjaxResult nursing(@RequestParam(defaultValue = "1", value = "page") Integer pageNum,
                              @RequestParam(defaultValue = "10", value = "limit") Integer pageSize,
                              @RequestParam(defaultValue = ""  , value = "nurseRank") String usr
    ){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        if (usr == null || usr.equals("")) {
            map.put("usr","");
            List<Nursing> nursings = nursingService.listAll(map);
            PageInfo<Nursing> page = new PageInfo<>(nursings);
            return successData(page.getTotal(), nursings);
        }
        map.put("usr",usr);
        List<Nursing> nursings = nursingService.listAll(map);
        PageInfo<Nursing> page = new PageInfo<>(nursings);
        return successData(page.getTotal(), nursings);
    }



    //==============添加功能=================

    /** 添加管理员 */
    @RequestMapping("/addAdmin")
    public AjaxResult addAmin(AdminInfo adminInfo){
        int insert = adminInfoService.insert(adminInfo);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }

    /** 添加用户 */
    @RequestMapping("/addUsr")
    public AjaxResult addUsr(UsrInfo usrInfo){
        UsrInfo results = usrInfoService.sltName(usrInfo);
        if (results != null){
            return error("添加失败,登录名已被注册");
        }
        int insert = usrInfoService.insert(usrInfo);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }


    @RequestMapping("/addHealth")
    public AjaxResult addHealth(HealthRecords healthRecords){
        System.out.println(healthRecords);
        int insert = healthRecordsService.insert(healthRecords);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }

    @RequestMapping("/addHigh")
    public AjaxResult addHigh(HighRisk highRisk){
        System.out.println(highRisk);
        int insert = highRiskService.insert(highRisk);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }

    @RequestMapping("/addMedication")
    public AjaxResult addMedication(Medication medication){
        System.out.println(medication);
        int insert = medicationService.insert(medication);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }


    @RequestMapping("/addMon")
    public AjaxResult addMon(MonthlyCatering monthlyCatering){
        System.out.println(monthlyCatering);
        int insert = monthlyCateringService.insert(monthlyCatering);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }


    @RequestMapping("/addOut")
    public AjaxResult addOut(GoOut goOut){
        System.out.println(goOut);
        int insert = goOutService.insert(goOut);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }


    @RequestMapping("/addOlder")
    public AjaxResult addOlder(OlderInfo olderInfo){
        System.out.println(olderInfo);
        int insert = olderInfoService.insert(olderInfo);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }


    @RequestMapping("/addDorm")
    public AjaxResult addDorm(DormitoryAllocation dormitoryAllocation){
        System.out.println(dormitoryAllocation);
        int insert = dormitoryAllocationService.insert(dormitoryAllocation);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }




    @RequestMapping("/addCheck")
    public AjaxResult addCheck(CheckIn checkIn){
        System.out.println(checkIn);
        int insert = checkInService.insert(checkIn);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }

    @RequestMapping("/addNurs")
    public AjaxResult addNurs(Nursing nursing){
        System.out.println(nursing);
        int insert = nursingService.insert(nursing);
        if (1 == insert){
            return success("添加成功");
        }
        return error("添加失败");
    }




    //==============修改功能=================

    /** 修改管理员 */
    @RequestMapping("/modifyAdmin")
    public AjaxResult modifyAmin(AdminInfo adminInfo){
        int update = adminInfoService.update(adminInfo);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyUsr")
    public AjaxResult modifyUsr(UsrInfo usrInfo){
        int update = usrInfoService.update(usrInfo);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyHealth")
    public AjaxResult modifyHealth(HealthRecords healthRecords){
        System.out.println(healthRecords);
        int update = healthRecordsService.update(healthRecords);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }


    @RequestMapping("/modifyHigh")
    public AjaxResult modifyHigh(HighRisk highRisk){
        System.out.println(highRisk);
        int update = highRiskService.update(highRisk);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }


    @RequestMapping("/modifyMedication")
    public AjaxResult modifyMedication(Medication medication){
        System.out.println(medication);
        int update = medicationService.update(medication);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyMon")
    public AjaxResult modifyMon(MonthlyCatering monthlyCatering){
        System.out.println(monthlyCatering);
        int update = monthlyCateringService.update(monthlyCatering);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyOut")
    public AjaxResult modifyOut(GoOut goOut){
        System.out.println(goOut);
        int update = goOutService.update(goOut);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyOlder")
    public AjaxResult modifyOlder(OlderInfo olderInfo){
        int update = olderInfoService.update(olderInfo);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyDorm")
    public AjaxResult modifyDorm(DormitoryAllocation dormitoryAllocation){
        int update = dormitoryAllocationService.update(dormitoryAllocation);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }




    @RequestMapping("/modifyCheck")
    public AjaxResult modifyCheck(CheckIn checkIn){
        int update = checkInService.update(checkIn);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }

    @RequestMapping("/modifyNurs")
    public AjaxResult modifyNurs(Nursing nursing){
        int update = nursingService.update(nursing);
        if (1 == update){
            return success("修改成功");
        }
        return error("修改失败");
    }




    //==============删除功能=================


    /** 删除管理员 */
    @RequestMapping("/delAdmin")
    public AjaxResult delAdmin(Integer adminId){
        adminInfoService.delAdmin(adminId);
        return success("删除成功");
    }
    /** 批量删除管理员 */
    @RequestMapping("/batchDelAdmin")
    public AjaxResult batchDel(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    adminInfoService.delAdmin(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    /** 删除用户 */
    @RequestMapping("/delUsr")
    public AjaxResult delUsr(Integer usrId){
        usrInfoService.delete(usrId);
        return success("删除成功");
    }
    /** 批量删除用户 */
    @RequestMapping("/batchDelUsr")
    public AjaxResult batchDelUsr(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    usrInfoService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }


    @RequestMapping("/delHealth")
    public AjaxResult delHealth(Integer id){
        healthRecordsService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelHealth")
    public AjaxResult batchDelHealth(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    healthRecordsService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    @RequestMapping("/delHigh")
    public AjaxResult delHigh(Integer id){
        highRiskService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelHigh")
    public AjaxResult batchDelHigh(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    highRiskService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    @RequestMapping("/delMedication")
    public AjaxResult delMedication(Integer id){
        medicationService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelMedication")
    public AjaxResult batchDelMedication(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    medicationService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }


    @RequestMapping("/delMon")
    public AjaxResult delMon(Integer id){
        monthlyCateringService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelMon")
    public AjaxResult batchDelMon(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    monthlyCateringService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    @RequestMapping("/delOut")
    public AjaxResult delOut(Integer id){
        goOutService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelOut")
    public AjaxResult batchDelOut(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    goOutService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }


    @RequestMapping("/delOlder")
    public AjaxResult delOlder(@RequestParam(value = "olderId") Integer id){
        olderInfoService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelOlder")
    public AjaxResult batchDelOlder(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    olderInfoService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    @RequestMapping("/delDorm")
    public AjaxResult delDorm(@RequestParam(value = "id") Integer id){
        dormitoryAllocationService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelDorm")
    public AjaxResult batchDelDorm(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    dormitoryAllocationService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }



    @RequestMapping("/delCheck")
    public AjaxResult delCheck(@RequestParam(value = "id") Integer id){
        checkInService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelCheck")
    public AjaxResult batchDelCheck(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    checkInService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }

    @RequestMapping("/delNurs")
    public AjaxResult delNurs(@RequestParam(value = "id") Integer id){
        nursingService.delete(id);
        return success("删除成功");
    }

    @RequestMapping("/batchDelNurs")
    public AjaxResult batchDelNurs(String listStr){
        if (null != listStr && !"".equals(listStr)){
            String[] ids = listStr.split(",");
            for (String id:ids) {
                if (null != id && !"".equals(id)) {
                    System.out.println(id);
                    nursingService.delete(Integer.valueOf(id));
                }
            }
        }
        return success("删除成功");
    }




    /** root 修改密码 */
    @RequestMapping("/altPwd")
    public AjaxResult altPwd(String pwd,String rpwd) {
        if(!pwd.equals(rpwd)) return error( "两次密码不一致");

        if (pwd != null && !pwd.equals("")) {
            int i = rootInfoService.altPwd(pwd);
            if (i != 0) {
                return success(0, "修改成功");
            }
        }
        return error("密码不允许为空");
    }

    @RequestMapping("/adminAltPwd")
    public AjaxResult adminAltPwd(String pwd,String rpwd,Map map,HttpServletRequest request) {
        if(!pwd.equals(rpwd)) return error( "两次密码不一致");

        if (pwd != null && !pwd.equals("")) {
            AdminInfo admin= (AdminInfo) request.getSession().getAttribute("admin");
            Long id = admin.getAdminId();
            map.put("pwd",pwd);
            map.put("id",id);
            int i = adminInfoService.altPwd(map);
            if (i != 0) {
                return success(0, "修改成功");
            }
        }
        return error("密码不允许为空");
    }

    @RequestMapping("/usrAltPwd")
    public AjaxResult usrAltPwd(String pwd,String rpwd,Map map,HttpServletRequest request) {
        if(!pwd.equals(rpwd)) return error( "两次密码不一致");

        if (pwd != null && !pwd.equals("")) {
            UsrInfo usr = (UsrInfo) request.getSession().getAttribute("usr");
            Long id = usr.getUsrId();
            map.put("pwd",pwd);
            map.put("id",id);
            int i = usrInfoService.altPwd(map);
            if (i != 0) {
                return success(0, "修改成功");
            }
        }
        return error("密码不允许为空");
    }








}
