using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations;
using RSDB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace RSDB
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<equipment>().Property(p => p.unitPrice).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<equipment>().Property(p => p.depreciationRate).HasColumnType("decimal(18,4)");
            //modelBuilder.Entity<equipment>().Property(p => p.depreciationDate).HasDefaultValue(DateTime.Now.Date);

            modelBuilder.Entity<d_salary>().Property(p => p.pay).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.insuranceFund).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.workDays).HasColumnType("decimal(5,2)");

            modelBuilder.Entity<d_material>().Property(p => p.quantity).HasColumnType("decimal(18,6)");
            modelBuilder.Entity<d_material>().Property(p => p.remainQuantity).HasColumnType("decimal(18,6)");
            modelBuilder.Entity<d_material>().Property(p => p.unitPrice).HasColumnType("decimal(18,6)");
            //d_design
            modelBuilder.Entity<d_design>().Property(p => p.dFee).HasColumnType("decimal(18,2)");

            //d_inspection
            modelBuilder.Entity<d_inspection>().Property(p => p.expense).HasColumnType("decimal(18,2)");

            modelBuilder.Entity<d_energy>().Property(p => p.quantity).HasColumnType("decimal(18,6)");
            modelBuilder.Entity<d_energy>().Property(p => p.remainAmount).HasColumnType("decimal(18,6)");

            modelBuilder.Entity<d_energy>().Property(p => p.unitPrice).HasColumnType("decimal(18,6)");

            modelBuilder.Entity<p_material>().Property(p => p.used).HasColumnType("decimal(18,6)");

            //modelBuilder.Entity<p_energy>().Property(p => p.lastUpdateTime).HasDefaultValue(DateTime.Now);
            //modelBuilder.Entity<p_attendance>().Property(p => p.lastUpdateTime).HasDefaultValue(DateTime.Now);
            //modelBuilder.Entity<p_design>().Property(p => p.lastUpdateTime).HasDefaultValue(DateTime.Now);
            //modelBuilder.Entity<p_inspection>().Property(p => p.lastUpdateTime).HasDefaultValue(DateTime.Now);
            //modelBuilder.Entity<p_material>().Property(p => p.lastUpdateTime).HasDefaultValue(DateTime.Now);

            byte[] arr = new byte[31];

            modelBuilder.Entity<d_attendance>().Property(b => b.remainAttData).HasDefaultValue(string.Join(',', arr));
            modelBuilder.Entity<d_equipment>().Property(b => b.remainEquData).HasDefaultValue(string.Join(',', arr));
            modelBuilder.Entity<p_project>().Property(b => b.parentId).HasDefaultValue(0);
            modelBuilder.Entity<p_energy>().Property(b => b.totalYield).HasDefaultValue(0);
            modelBuilder.Entity<p_energy>().Property(b => b.rdYield).HasDefaultValue(0);
            modelBuilder.Entity<p_rdEquipment>().Property(b => b.rdRatio).HasDefaultValue(0);
            modelBuilder.Entity<p_yield_config>().Property(b => b.selected).HasDefaultValue(1);
            // modelBuilder.Entity<p_attendance>().Property(p => p.pay).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.insuranceFund).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdPay).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdInsuranceFund).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<p_summary>().Property(p => p.rdFunds).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<p_docFile>().Property(b => b.finished).HasDefaultValue(true);
            modelBuilder.Entity<p_reviewCommittee>().Property(b => b.seq).HasDefaultValue(1);
            modelBuilder.Entity<p_docFile_attachment>().Property(b=>b.fileType).HasDefaultValue(99);


            // modelBuilder.Entity<p_attendance>().Property(p => p.endowmentOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.houseOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.injuryOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.maternityOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.medicalOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.unemploymentOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdEndowmentOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdHouseOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdInjuryOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdUnemploymentOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdMaternityOfCom).HasColumnType("decimal(18,2)");
            // modelBuilder.Entity<p_attendance>().Property(p => p.rdMedicalOfCom).HasColumnType("decimal(18,2)");

            modelBuilder.Entity<d_salary>().Property(p => p.endowmentOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.houseOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.injuryOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.maternityOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.medicalOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.unemploymentOfCom).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.endowment).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.house).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.injury).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.maternity).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.medical).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<d_salary>().Property(p => p.unemployment).HasColumnType("decimal(18,2)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.endowmentOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.houseOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.injuryOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.maternityOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.medicalOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.unemploymentOfCom).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.endowment).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.house).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.injury).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.maternity).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.medical).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<sys_insuranceConfig>().Property(p => p.unemployment).HasColumnType("decimal(6,4)");
            modelBuilder.Entity<p_project>().Property(p => p.result).HasDefaultValue("0");
            //唯一索引
            modelBuilder.Entity<p_summary>().HasIndex(a => new { a.projectId, a.month, a.rdType }).IsUnique(true);
            //  modelBuilder.Entity<p_attendance_used>().HasIndex(a => new { a.companyId, a.enumber, a.month }).IsUnique(true);
            modelBuilder.Entity<p_attendance_used>().HasIndex(a => new { a.companyId, a.enumber, a.workDate }).IsUnique(true);
            // modelBuilder.Entity<p_attendance>().HasIndex(a => new { a.companyId, a.enumber, a.workDate }).IsUnique(true);
            modelBuilder.Entity<p_equipment_used>().HasIndex(a => new { a.companyId, a.ecode, a.month }).IsUnique(true);
            //modelBuilder.Entity<p_equipment>().HasIndex(a => new { a.companyId, a.ecode, a.month }).IsUnique(true);
            modelBuilder.Entity<d_equipment>().HasIndex(a => new { a.ecode, a.month, a.companyId }).IsUnique(true);
            modelBuilder.Entity<d_salary>().HasIndex(a => new { a.enumber, a.month, a.companyId }).IsUnique(true);
            modelBuilder.Entity<equipment>().HasIndex(a => new { a.companyId, a.ecode }).IsUnique(true);
            modelBuilder.Entity<d_bonus>().HasIndex(a => new { a.companyId, a.enumber, a.month }).IsUnique(true);
            modelBuilder.Entity<insuranceConfig>().HasIndex(a => new { a.companyId }).IsUnique(true);
            modelBuilder.Entity<p_attendance_month>().HasIndex(a => new { a.companyId, a.projectId, a.month }).IsUnique(true);
            modelBuilder.Entity<c_attendance>().HasIndex(a => new { a.companyId, a.enumber, a.workDate }).IsUnique(true);
            modelBuilder.Entity<employee>().HasIndex(a => new { a.companyId, a.enumber }).IsUnique(true);
            modelBuilder.Entity<p_adjust>().HasIndex(a => new { a.pRdId, a.rdType }).IsUnique(true);
            modelBuilder.Entity<p_rdEmployee_plan>().HasIndex(a => new { a.companyId, a.projectId, a.enumber, a.month }).IsUnique(true);
            modelBuilder.Entity<c_financial_info>().HasIndex(a => new { a.companyId, a.year, a.key }).IsUnique(true);
            modelBuilder.Entity<c_holiday>().HasIndex(a => new { a.companyId, a.month }).IsUnique(true);
            modelBuilder.Entity<p_info_summary>().HasIndex(a => new { a.companyId, a.projectId, a.month }).IsUnique(true);
            modelBuilder.Entity<kafka_queue>().HasIndex(a => new { a.key }).IsUnique(true);
            modelBuilder.Entity<t_investment_invoice>().HasIndex(a => new { a.invoiceDetailId }).IsUnique(true);
            modelBuilder.Entity<t_investment_contract>().HasIndex(a => new { a.contactDetailId }).IsUnique(true);
            modelBuilder.Entity<t_investment_bankReceipt>().HasIndex(a => new { a.investmentId, a.bankReceiptId }).IsUnique(true);
            modelBuilder.Entity<p_rdHour_config>().HasIndex(a => new { a.companyId, a.projectId, a.type, a.month }).IsUnique(true);
            modelBuilder.Entity<p_rdAgg_config>().HasIndex(a => new { a.companyId, a.type, a.month }).IsUnique(true);
            modelBuilder.Entity<p_outsourcing>().HasIndex(a => new { a.companyId, a.projectId, a.type, a.month }).IsUnique(true);
            modelBuilder.Entity<p_fina_schedule>().HasIndex(a => new { a.companyId, a.projectId, a.month, a.deptName }).IsUnique(true);
            modelBuilder.Entity<p_audit_report>().HasIndex(a => new { a.projectId, a.moduleId }).IsUnique(true);
            modelBuilder.Entity<p_year_info>().HasIndex(a => new { a.projectId, a.year }).IsUnique(true);
            modelBuilder.Entity<c_rd_summary>().HasIndex(a => new { a.companyId, a.year }).IsUnique(true);
            modelBuilder.Entity<p_meet_member>().HasIndex(a => new { a.projectId }).IsUnique(true);
            modelBuilder.Entity<p_audit_proposal>().HasIndex(a => new { a.proposalId }).IsUnique(true);
            modelBuilder.Entity<p_audit_result>().HasIndex(a => new { a.documentId }).IsUnique(true);
            modelBuilder.Entity<p_sense>().HasIndex(a => new { a.companyId, a.type, a.preachDate }).IsUnique(true);
            modelBuilder.Entity<p_trial_config>().HasIndex(a => new { a.companyId }).IsUnique(true);
            modelBuilder.Entity<high_tech_score>().HasIndex(a => new { a.companyId, a.year }).IsUnique(true);
            modelBuilder.Entity<high_finance_score>().HasIndex(a => new { a.companyId, a.year }).IsUnique(true);
            modelBuilder.Entity<p_audit_achievement>().HasIndex(a => new { a.achievementId }).IsUnique(true);
            modelBuilder.Entity<build_config>().HasIndex(a => new { a.companyId, a.year, a.type }).IsUnique(true);
            modelBuilder.Entity<company_group>().HasIndex(a => new { a.companyId, a.groupId }).IsUnique(true);
            modelBuilder.Entity<c_revenue>().HasIndex(a => new { a.companyId, a.year, a.month }).IsUnique(true);
            modelBuilder.Entity<c_year_cost>().HasIndex(a => new { a.companyId, a.year, a.rdType }).IsUnique(true);
            modelBuilder.Entity<c_document>().HasIndex(a => new { a.companyId, a.year, a.type }).IsUnique(true);
            modelBuilder.Entity<c_rd_funds>().HasIndex(a => new { a.companyId, a.year, a.month, a.type }).IsUnique(true);
            modelBuilder.Entity<p_audit_rdFee>().HasIndex(a => new { a.companyId, a.month, a.projectId, a.rdType }).IsUnique(true);
            modelBuilder.Entity<p_year_fee>().HasIndex(a => new { a.companyId, a.year }).IsUnique(true);
            modelBuilder.Entity<c_stage>().HasIndex(a => new { a.companyId, a.stageKey }).IsUnique(true);
            modelBuilder.Entity<p_audit_opt>().HasIndex(a=>new {a.companyId,a.projectId,a.year}).IsUnique(true);
            modelBuilder.Entity<c_worker>().HasIndex(a=>new {a.companyId,a.year}).IsUnique(true);
            modelBuilder.Entity<p_deliver>().HasIndex(a=>new {a.companyId,a.projectId,a.month,a.deliverType}).IsUnique(true);
            modelBuilder.Entity<c_product_year>().HasIndex(a=>new {a.companyId,a.productId,a.year}).IsUnique(true);
            modelBuilder.Entity<salaryConfig>().HasIndex(a=>new {a.companyId,a.type}).IsUnique(true);
            modelBuilder.Entity<stEmployee>().HasIndex(a=>new {a.companyId,a.year,a.enumber}).IsUnique(true);
            modelBuilder.Entity<p_docFile_footer>().HasIndex(a=>new {a.companyId,a.year,a.projectId}).IsUnique(true);
            base.OnModelCreating(modelBuilder);

        }
        public static void InitData(MigrationBuilder migrationBuilder)
        {
            var dUser = "ysadmin";
            var dPwd = "ysadmin!@#$";
            string hashPwd = null;
            using (var md5 = MD5.Create())
            {
                var result = md5.ComputeHash(Encoding.UTF8.GetBytes(dUser + dPwd));
                hashPwd = BitConverter.ToString(result).Replace("-", "");
            }
            migrationBuilder.InsertData("user", new[] { "companyId", "userName", "password", "realName", "gender", "tel", "status", "avatar", "remark", "creatorId", "createTime" }
                            , new object[] { 0, dUser, hashPwd, "管理员", 1, "", 1, "", "初始用户", -1, DateTime.Now });
        }
        public DbSet<user> user { get; set; }
        public DbSet<sys_session> sys_session { get; set; }
        public DbSet<app_menu> app_menu { get; set; }
        public DbSet<dept> dept { get; set; }
        public DbSet<equipment> equipment { get; set; }
        public DbSet<employee> employee { get; set; }
        public DbSet<company> company { get; set; }

        public DbSet<d_attendance> d_attendance { get; set; }
        public DbSet<d_design> d_design { get; set; }
        public DbSet<d_energy> d_energy { get; set; }
        public DbSet<d_equipment> d_equipment { get; set; }
        public DbSet<d_inspection> d_inspection { get; set; }

        public DbSet<d_material> d_material { get; set; }
        public DbSet<d_salary> d_salary { get; set; }
        public DbSet<p_report> p_report { get; set; }
        public DbSet<p_project> p_project { get; set; }
        public DbSet<p_design> p_design { get; set; }
        public DbSet<p_inspection> p_inspection { get; set; }
        public DbSet<p_material> p_material { get; set; }

        public DbSet<p_energy> p_energy { get; set; }

        public DbSet<p_attendance> p_attendance { get; set; }
        public DbSet<p_equipment> p_equipment { get; set; }

        public DbSet<accountTitle> accountTitle { get; set; }

        public DbSet<p_summary> p_summary { get; set; }

        public DbSet<i_equipment> i_equipment { get; set; }
        public DbSet<i_member> i_member { get; set; }
        public DbSet<sys_insuranceConfig> sys_insuranceConfig { get; set; }
        public DbSet<app_role> app_role { get; set; }
        public DbSet<app_role_menu> app_role_menu { get; set; }
        public DbSet<app_user_role> app_user_role { get; set; }

        public DbSet<docTemplate> docTemplate { get; set; }
        public DbSet<docProcess> docProcess { get; set; }
        public DbSet<docFile> docFile { get; set; }
        public DbSet<docFileItem> docFileItem { get; set; }
        public DbSet<docProcessTemplate> docProcessTemplate { get; set; }
        public DbSet<p_document> p_document { get; set; }
        public DbSet<p_stage> p_stage { get; set; }
        public DbSet<sys_dictionary> sys_dictionary { get; set; }

        public DbSet<rdDept> rdDept { get; set; }

        public DbSet<p_worktimeRatio> p_worktimeRatio { get; set; }
        public DbSet<p_attendance_used> p_attendance_used { get; set; }
        public DbSet<p_equipment_used> p_equipment_used { get; set; }

        public DbSet<d_bonus> d_bonus { get; set; }

        public DbSet<insuranceConfig> insuranceConfig { get; set; }

        public DbSet<p_attendance_month> p_attendance_month { get; set; }
        public DbSet<sys_tableField> sys_tableField { get; set; }
        public DbSet<app_manager_role> app_manager_role { get; set; }

        public DbSet<app_manager_user> app_manager_user { get; set; }
        public DbSet<sys_document> sys_document { get; set; }
        public DbSet<t_project> t_project { get; set; }

        public DbSet<t_projectAppendix> t_projectAppendix { get; set; }
        public DbSet<t_projectBasic> t_projectBasic { get; set; }
        public DbSet<t_projectImplement> t_projectImplement { get; set; }
        public DbSet<t_projectInvestMent> t_projectInvestMent { get; set; }
        public DbSet<t_projectOther> t_projectOther { get; set; }
        public DbSet<t_projectQuota> t_projectQuota { get; set; }


        public DbSet<c_annualReport> c_annualReport { get; set; }
        public DbSet<c_financialCondition> c_financialCondition { get; set; }
        public DbSet<c_ownership> c_ownership { get; set; }
        public DbSet<c_support> c_support { get; set; }
        public DbSet<t_declaration> t_declaration { get; set; }

        public DbSet<t_projectCost> t_projectCost { get; set; }
        public DbSet<p_file> p_file { get; set; }
        public DbSet<rdAccountDetail> rdAccountDetail { get; set; }
        public DbSet<p_budget> p_budget { get; set; }
        public DbSet<c_cost> c_cost { get; set; }

        public DbSet<c_activity> c_activity { get; set; }

        public DbSet<p_material_trace> p_material_trace { get; set; }

        public DbSet<sys_docList> sys_docList { get; set; }

        public DbSet<expert> expert { get; set; }

        public DbSet<rdEmployee> rdEmployee { get; set; }

        public DbSet<sys_docOperator> sys_docOperator { get; set; }
        public DbSet<workshop> workshop { get; set; }
        public DbSet<p_patent> p_patent { get; set; }

        public DbSet<rdEquipment> rdEquipment { get; set; }

        public DbSet<app_company_role> app_company_role { get; set; }

        public DbSet<c_attendance> c_attendance { get; set; }
        public DbSet<sys_log> sys_log { get; set; }

        // public DbSet<contractTraceability> contractTraceability { get; set; }
        // 修改了表名：salaryConfig-->fieldConfig
        public DbSet<salaryConfig> fieldConfig { get; set; }

        public DbSet<p_project_number> p_project_number { get; set; }

        public DbSet<p_trialProd> p_trialProd { get; set; }

        public DbSet<p_docFile> p_docFile { get; set; }

        public DbSet<p_docFile_data> p_docFile_data { get; set; }

        public DbSet<p_rdEmployee> p_rdEmployee { get; set; }

        public DbSet<p_rdEquipment> p_rdEquipment { get; set; }

        public DbSet<docFileTemplate> docFileTemplate { get; set; }

        public DbSet<p_project_status> p_project_status { get; set; }


        public DbSet<t_step> t_step { get; set; }

        public DbSet<t_step_status> t_step_status { get; set; }

        public DbSet<t_step_detail> t_step_detail { get; set; }
        public DbSet<tech_requirement> tech_requirement { get; set; }
        public DbSet<patent> patent { get; set; }
        public DbSet<patent_apply> patent_apply { get; set; }
        public DbSet<patent_cost> patent_cost { get; set; }
        public DbSet<p_docFile_trial> p_docFile_trial { get; set; }
        public DbSet<p_patent_plan> p_patent_plan { get; set; }
        public DbSet<d_voucher> d_voucher { get; set; }
        public DbSet<p_voucher> p_voucher { get; set; }
        public DbSet<p_budget_status> p_budget_status { get; set; }
        public DbSet<t_attachments> t_attachments { get; set; }
        public DbSet<p_adjust> p_adjust { get; set; }
        public DbSet<p_reviewCommittee> p_reviewCommittee { get; set; }
        public DbSet<p_yield_config> p_yield_config { get; set; }
        public DbSet<t_product> t_product { get; set; }
        public DbSet<t_direction> t_direction { get; set; }
        public DbSet<t_product_stage> t_product_stage { get; set; }
        public DbSet<t_product_stage_list> t_product_stage_list { get; set; }
        public DbSet<t_project_list> t_project_list { get; set; }
        public DbSet<employeeOpenid> employeeOpenid { get; set; }
        public DbSet<e_user> e_user { get; set; }
        public DbSet<e_user_info> e_user_info { get; set; }
        public DbSet<e_extra> e_extra { get; set; }
        public DbSet<e_bank_info> e_bank_info { get; set; }
        public DbSet<e_intellectual_property> e_intellectual_property { get; set; }
        public DbSet<e_award> e_award { get; set; }
        public DbSet<e_paper> e_paper { get; set; }
        public DbSet<e_book> e_book { get; set; }
        public DbSet<p_rdEmployee_plan> p_rdEmployee_plan { get; set; }
        public DbSet<p_audit> p_audit { get; set; }
        public DbSet<c_financial_info> c_financial_info { get; set; }
        public DbSet<c_bank_info> c_bank_info { get; set; }
        public DbSet<c_extra> c_extra { get; set; }
        public DbSet<c_employment_info> c_employment_info { get; set; }
        public DbSet<pushAttendance_log> pushAttendance_log { get; set; }
        public DbSet<c_holiday> c_holiday { get; set; }
        public DbSet<p_audit_docFile> p_audit_docFile { get; set; }
        public DbSet<p_project_state> p_project_state { get; set; }
        public DbSet<c_setting> c_setting { get; set; }
        public DbSet<p_docFile_footer> p_docFile_footer { get; set; }
        public DbSet<p_situation_summary> p_situation_summary { get; set; }
        public DbSet<p_problem_summary> p_problem_summary { get; set; }
        public DbSet<p_standard_implement> p_standard_implement { get; set; }
        public DbSet<p_policy_summary> p_policy_summary { get; set; }
        public DbSet<p_info_summary> p_info_summary { get; set; }
        public DbSet<c_backup_data> c_backup_data { get; set; }
        public DbSet<kafka_queue> kafka_queue { get; set; }
        public DbSet<t_beian> t_beian { get; set; }
        public DbSet<t_equipment> t_equipment { get; set; }
        public DbSet<t_investment> t_investment { get; set; }
        public DbSet<t_contract> t_contract { get; set; }
        public DbSet<t_contract_detail> t_contract_detail { get; set; }
        public DbSet<t_invoice> t_invoice { get; set; }
        public DbSet<t_invoice_detail> t_invoice_detail { get; set; }
        public DbSet<t_nameplate> t_nameplate { get; set; }
        public DbSet<t_bankReceipt> t_bankReceipt { get; set; }
        public DbSet<t_investment_contract> t_investment_contract { get; set; }
        public DbSet<t_investment_invoice> t_investment_invoice { get; set; }
        public DbSet<t_investment_bankReceipt> t_investment_bankReceipt { get; set; }
        public DbSet<t_project_beian> t_project_beian { get; set; }
        public DbSet<p_audit_report> p_audit_report { get; set; }
        public DbSet<p_rdHour_config> p_rdHour_config { get; set; }
        public DbSet<docFileStage> docFileStage { get; set; }
        public DbSet<p_rdAgg_config> p_rdAgg_config { get; set; }
        public DbSet<highTech> highTech { get; set; }
        public DbSet<highTech_income> highTech_income { get; set; }
        public DbSet<highTech_detail> highTech_detail { get; set; }
        public DbSet<highTech_project> highTech_project { get; set; }
        public DbSet<highTech_file> highTech_file { get; set; }
        public DbSet<p_audit_patent> p_audit_patent { get; set; }
        public DbSet<p_patent_opinion> p_patent_opinion { get; set; }
        public DbSet<p_outsourcing> p_outsourcing { get; set; }
        public DbSet<p_fina_schedule> p_fina_schedule { get; set; }
        public DbSet<p_docFile_attachment> p_docFile_attachment { get; set; }
        public DbSet<p_year_info> p_year_info { get; set; }
        public DbSet<c_rd_summary> c_rd_summary { get; set; }
        public DbSet<p_patent_file> p_patent_file { get; set; }
        public DbSet<proposal_management> proposal_management { get; set; }
        public DbSet<p_meet_member> p_meet_member { get; set; }
        public DbSet<p_audit_proposal> p_audit_proposal { get; set; }
        public DbSet<p_audit_result> p_audit_result { get; set; }
        public DbSet<keypoint_statistic> keypoint_statistic { get; set; }
        public DbSet<p_sense> p_sense { get; set; }
        public DbSet<p_trial_config> p_trial_config { get; set; }
        public DbSet<softRegistration> softRegistration { get; set; }
        public DbSet<ICRegistration> ICRegistration { get; set; }
        public DbSet<high_tech_score> high_tech_score { get; set; }
        public DbSet<high_finance_score> high_finance_score { get; set; }
        public DbSet<p_achievement> p_achievement { get; set; }
        public DbSet<p_achievement_file> p_achievement_file { get; set; }
        public DbSet<p_audit_achievement> p_audit_achievement { get; set; }
        public DbSet<build_config> build_config { get; set; }
        public DbSet<company_group> company_group { get; set; }
        public DbSet<c_revenue> c_revenue { get; set; }
        public DbSet<c_year_cost> c_year_cost { get; set; }
        public DbSet<c_document> c_document { get; set; }
        public DbSet<c_rd_funds> c_rd_funds { get; set; }
        public DbSet<c_name_history> c_name_history { get; set; }
        public DbSet<p_docFile_meeting> p_docFile_meeting { get; set; }
        public DbSet<proposal_list> proposal_list { get; set; }
        public DbSet<p_audit_rdFee> p_audit_rdFee { get; set; }
        public DbSet<p_year_fee> p_year_fee { get; set; }
        public DbSet<c_stage> c_stage { get; set; }
        public DbSet<docFileType> docFileType { get; set; }
        public DbSet<p_audit_opt> p_audit_opt { get; set; }
        public DbSet<p_deliver> p_deliver { get; set; }
        public DbSet<p_deliver_log> p_deliver_log { get; set; }
        public DbSet<c_worker> c_worker { get; set; } 
        public DbSet<c_product> c_product { get; set; } 
        public DbSet<c_product_year> c_product_year { get; set; }
        public DbSet<stEmployee> stEmployee { get; set; }
        public DbSet<p_change> p_change { get; set; }
        public DbSet<t_beian_summary> t_beian_summary { get; set; }
        public DbSet<t_beian_changed> t_beian_changed { get; set; }
        public DbSet<t_payment> t_payment { get; set; }
         
    }
}
