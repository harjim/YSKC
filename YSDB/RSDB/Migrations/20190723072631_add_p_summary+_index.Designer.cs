﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using RSDB;

namespace RSDB.Migrations
{
    [DbContext(typeof(DataContext))]
    [Migration("20190723072631_add_p_summary+_index")]
    partial class add_p_summary_index
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("RSDB.Models.accountTitle", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("accountName")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("accountNumber")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int>("accoutType");

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("parentId");

                    b.HasKey("id");

                    b.ToTable("accountTitle");
                });

            modelBuilder.Entity("RSDB.Models.app_menu", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("fullPath")
                        .HasMaxLength(300);

                    b.Property<string>("icon")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<byte>("level");

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("parentId");

                    b.Property<string>("perms");

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<byte>("seq");

                    b.Property<byte>("status");

                    b.Property<byte>("type");

                    b.Property<string>("url")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("app_menu");
                });

            modelBuilder.Entity("RSDB.Models.company", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("accountSystem")
                        .HasMaxLength(20);

                    b.Property<string>("addressCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("businessLicense")
                        .IsRequired()
                        .HasMaxLength(80);

                    b.Property<string>("capital")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("companyAddress")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("companyName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("creditCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("depts");

                    b.Property<string>("domain")
                        .HasMaxLength(100);

                    b.Property<string>("email")
                        .HasMaxLength(100);

                    b.Property<DateTime?>("firstDevFee");

                    b.Property<byte>("from");

                    b.Property<bool>("hasDevAccount");

                    b.Property<bool>("highTec");

                    b.Property<string>("highTecIndustry")
                        .HasMaxLength(20);

                    b.Property<string>("industryCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("invoiceTitle")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("linkMan")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("linkTel")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("logo")
                        .IsRequired()
                        .HasMaxLength(80);

                    b.Property<string>("mainIndustry")
                        .IsRequired()
                        .HasMaxLength(10);

                    b.Property<int>("members");

                    b.Property<string>("owner")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("realTaxAuthorities")
                        .HasMaxLength(30);

                    b.Property<DateTime>("registerTime");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<byte>("status");

                    b.Property<string>("taxAuthorities")
                        .HasMaxLength(30);

                    b.Property<string>("taxpayerId")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("id");

                    b.ToTable("company");
                });

            modelBuilder.Entity("RSDB.Models.d_attendance", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("attData")
                        .IsRequired()
                        .HasMaxLength(120);

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("ename")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("enumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<DateTime>("month");

                    b.Property<string>("remainAttData")
                        .IsRequired()
                        .ValueGeneratedOnAdd()
                        .HasMaxLength(120)
                        .HasDefaultValue("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("d_attendance");
                });

            modelBuilder.Entity("RSDB.Models.d_design", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<decimal>("dFee")
                        .HasColumnType("decimal(10,2)");

                    b.Property<DateTime>("designDate");

                    b.Property<string>("dname")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("d_design");
                });

            modelBuilder.Entity("RSDB.Models.d_energy", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("ename");

                    b.Property<DateTime>("occDate");

                    b.Property<decimal>("quantity")
                        .HasColumnType("decimal(10,2)");

                    b.Property<decimal>("remainQuantiy")
                        .HasColumnType("decimal(10,2)");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<byte>("type");

                    b.Property<string>("unit")
                        .HasMaxLength(10);

                    b.Property<decimal>("unitPrice")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("d_energy");
                });

            modelBuilder.Entity("RSDB.Models.d_equipment", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("ecode")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("ename")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("equData")
                        .IsRequired()
                        .HasMaxLength(120);

                    b.Property<DateTime>("month");

                    b.Property<string>("remainEquData")
                        .IsRequired()
                        .ValueGeneratedOnAdd()
                        .HasMaxLength(120)
                        .HasDefaultValue("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("d_equipment");
                });

            modelBuilder.Entity("RSDB.Models.d_inspection", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("accDate");

                    b.Property<string>("accNumber")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<decimal>("expense")
                        .HasColumnType("decimal(10,2)");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("summary")
                        .IsRequired()
                        .HasMaxLength(120);

                    b.Property<byte>("type");

                    b.HasKey("id");

                    b.ToTable("d_inspection");
                });

            modelBuilder.Entity("RSDB.Models.d_material", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("acqDate");

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("mcode");

                    b.Property<string>("mname");

                    b.Property<decimal>("quantity")
                        .HasColumnType("decimal(10,2)");

                    b.Property<decimal>("remainQuantity")
                        .HasColumnType("decimal(10,2)");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("unit")
                        .HasMaxLength(10);

                    b.Property<decimal>("unitPrice")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("d_material");
                });

            modelBuilder.Entity("RSDB.Models.d_salary", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("ename")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<decimal>("endowment")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("endowmentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<string>("enumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<decimal>("house")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("houseOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("injury")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("injuryOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("insuranceFund")
                        .HasColumnType("decimal(10,2)");

                    b.Property<decimal>("maternity")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("maternityOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("medical")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("medicalOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<DateTime>("month");

                    b.Property<decimal>("pay")
                        .HasColumnType("decimal(10,2)");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<decimal>("unemployment")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("unemploymentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("workDays")
                        .HasColumnType("decimal(5,2)");

                    b.HasKey("id");

                    b.ToTable("d_salary");
                });

            modelBuilder.Entity("RSDB.Models.dept", b =>
                {
                    b.Property<short>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("deptName");

                    b.Property<string>("fullPath")
                        .HasMaxLength(300);

                    b.Property<string>("identity")
                        .IsRequired()
                        .HasMaxLength(80);

                    b.Property<int>("level");

                    b.Property<short>("parentId");

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.HasKey("id");

                    b.ToTable("dept");
                });

            modelBuilder.Entity("RSDB.Models.employee", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("deptId");

                    b.Property<DateTime>("edate");

                    b.Property<int>("eduLevel");

                    b.Property<string>("ename")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("enumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("etype");

                    b.Property<string>("idNumber")
                        .HasMaxLength(20);

                    b.Property<string>("position")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.HasKey("id");

                    b.ToTable("employee");
                });

            modelBuilder.Entity("RSDB.Models.equipment", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("depreciationDate");

                    b.Property<decimal>("depreciationRate")
                        .HasColumnType("decimal(10,4)");

                    b.Property<int>("deptId");

                    b.Property<string>("ecode")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("emodal")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("ename")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<DateTime>("purchaseDate");

                    b.Property<int>("quantity");

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<string>("unit")
                        .IsRequired()
                        .HasMaxLength(10);

                    b.Property<decimal>("unitPrice")
                        .HasColumnType("decimal(12,2)");

                    b.Property<int>("usagePower");

                    b.Property<int>("usefullife");

                    b.HasKey("id");

                    b.ToTable("equipment");
                });

            modelBuilder.Entity("RSDB.Models.i_equipment", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<int>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("ecode")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("projectId");

                    b.HasKey("id");

                    b.ToTable("i_equipment");
                });

            modelBuilder.Entity("RSDB.Models.i_member", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<int>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("enumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("projectId");

                    b.HasKey("id");

                    b.ToTable("i_member");
                });

            modelBuilder.Entity("RSDB.Models.p_attendance", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("attData")
                        .IsRequired()
                        .HasMaxLength(120);

                    b.Property<int>("attendanceDataId");

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<decimal>("endowmentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("houseOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("injuryOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("insuranceFund")
                        .HasColumnType("decimal(10,2)");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<decimal>("maternityOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("medicalOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("pay")
                        .HasColumnType("decimal(10,2)");

                    b.Property<int>("projectId");

                    b.Property<decimal>("rdEndowmentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("rdHouseOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("rdInjuryOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("rdInsuranceFund")
                        .HasColumnType("decimal(10,2)");

                    b.Property<decimal>("rdMaternityOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("rdMedicalOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("rdPay")
                        .HasColumnType("decimal(10,2)");

                    b.Property<decimal>("rdUnemploymentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.Property<decimal>("unemploymentOfCom")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("p_attendance");
                });

            modelBuilder.Entity("RSDB.Models.p_design", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("designDataId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("projectId");

                    b.HasKey("id");

                    b.ToTable("p_design");
                });

            modelBuilder.Entity("RSDB.Models.p_energy", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("energyDataId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("projectId");

                    b.Property<decimal>("used")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("p_energy");
                });

            modelBuilder.Entity("RSDB.Models.p_equipment", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<decimal>("depreciation")
                        .HasColumnType("decimal(12,2)");

                    b.Property<string>("equData")
                        .IsRequired()
                        .HasMaxLength(120);

                    b.Property<int>("equipmentDataId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("projectId");

                    b.Property<decimal>("rdDepreciation")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("p_equipment");
                });

            modelBuilder.Entity("RSDB.Models.p_inspection", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("inspectionDataId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("projectId");

                    b.HasKey("id");

                    b.ToTable("p_inspection");
                });

            modelBuilder.Entity("RSDB.Models.p_material", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("materialDataId");

                    b.Property<int>("projectId");

                    b.Property<decimal>("used")
                        .HasColumnType("decimal(12,2)");

                    b.HasKey("id");

                    b.ToTable("p_material");
                });

            modelBuilder.Entity("RSDB.Models.p_project", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("beginDate");

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("deptId");

                    b.Property<DateTime>("endDate");

                    b.Property<int>("estimateExpense");

                    b.Property<string>("masterENumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("pname");

                    b.Property<int>("rdIndex");

                    b.Property<int>("reportId");

                    b.Property<string>("tecIndustry")
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("p_project");
                });

            modelBuilder.Entity("RSDB.Models.p_report", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("rname")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("ryear");

                    b.HasKey("id");

                    b.ToTable("p_report");
                });

            modelBuilder.Entity("RSDB.Models.p_summary", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("accountNumber")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<DateTime>("month");

                    b.Property<int>("projectId");

                    b.Property<decimal>("rdFunds")
                        .HasColumnType("decimal(12,2)");

                    b.Property<byte>("rdType");

                    b.Property<int>("updatorId");

                    b.HasKey("id");

                    b.HasIndex("projectId", "month", "rdType")
                        .IsUnique();

                    b.ToTable("p_summary");
                });

            modelBuilder.Entity("RSDB.Models.sys_session", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<DateTime>("expireTime");

                    b.Property<string>("token")
                        .HasMaxLength(50);

                    b.Property<DateTime>("updateTime");

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("sys_session");
                });

            modelBuilder.Entity("RSDB.Models.user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("avatar")
                        .HasMaxLength(20);

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("email")
                        .HasMaxLength(100);

                    b.Property<byte>("gender");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("realName")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<byte>("status");

                    b.Property<string>("tel")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<byte>("type");

                    b.Property<string>("userName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("user");
                });
#pragma warning restore 612, 618
        }
    }
}
