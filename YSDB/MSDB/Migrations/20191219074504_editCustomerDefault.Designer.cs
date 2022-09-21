﻿// <auto-generated />
using System;
using MSDB;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace MSDB.Migrations
{
    [DbContext(typeof(DataContext))]
    [Migration("20191219074504_editCustomerDefault")]
    partial class editCustomerDefault
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.6-servicing-10079")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("MSDB.Models.app_menu", b =>
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

            modelBuilder.Entity("MSDB.Models.app_role", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("remark")
                        .HasMaxLength(100);

                    b.Property<string>("roleName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("app_role");
                });

            modelBuilder.Entity("MSDB.Models.app_role_data", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("dataType");

                    b.Property<int>("functionId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("roleId");

                    b.HasKey("id");

                    b.ToTable("app_role_data");
                });

            modelBuilder.Entity("MSDB.Models.app_role_menu", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("menuId");

                    b.Property<int>("roleId");

                    b.HasKey("id");

                    b.ToTable("app_role_menu");
                });

            modelBuilder.Entity("MSDB.Models.app_user_role", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("roleId");

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("app_user_role");
                });

            modelBuilder.Entity("MSDB.Models.customer", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("accountSystem")
                        .HasMaxLength(20);

                    b.Property<string>("addressCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int?>("capital");

                    b.Property<string>("capitalUnit")
                        .HasMaxLength(50);

                    b.Property<string>("companyAddress")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int?>("companyId");

                    b.Property<string>("companyName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("creatorTime");

                    b.Property<string>("creditCode")
                        .IsRequired()
                        .HasMaxLength(30);

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

                    b.Property<string>("mainIndustry")
                        .IsRequired()
                        .HasMaxLength(10);

                    b.Property<string>("owner")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("realTaxAuthorities")
                        .HasMaxLength(30);

                    b.Property<DateTime>("registerTime");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("taxAuthorities")
                        .HasMaxLength(30);

                    b.Property<string>("taxpayerId")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("id");

                    b.ToTable("customer");
                });

            modelBuilder.Entity("MSDB.Models.customer_account", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("customerId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("pUrl")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("platform")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.HasKey("id");

                    b.ToTable("customer_account");
                });

            modelBuilder.Entity("MSDB.Models.customer_user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("customerId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("mType");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("customer_user");
                });

            modelBuilder.Entity("MSDB.Models.d_user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("dingUserId")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("openid")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("unionid")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("d_user");
                });

            modelBuilder.Entity("MSDB.Models.expert", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("address")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<DateTime>("birthday");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("eduLevel");

                    b.Property<string>("email")
                        .HasMaxLength(30);

                    b.Property<string>("expertNumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<byte>("gender");

                    b.Property<string>("honour")
                        .HasMaxLength(1000);

                    b.Property<string>("idNumber")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("industryCode")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<DateTime?>("issueDate");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("level");

                    b.Property<string>("partHistory")
                        .IsRequired()
                        .HasMaxLength(1000);

                    b.Property<string>("phone")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("photoUrl")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<byte>("policitalStatus");

                    b.Property<string>("postcode");

                    b.Property<string>("qrcode")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("realName")
                        .IsRequired()
                        .HasMaxLength(15);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<byte>("status");

                    b.Property<string>("uuid")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime?>("validDate");

                    b.Property<string>("workHistory")
                        .IsRequired()
                        .HasMaxLength(1000);

                    b.HasKey("id");

                    b.ToTable("expert");
                });

            modelBuilder.Entity("MSDB.Models.h3_customer", b =>
                {
                    b.Property<string>("objectid")
                        .ValueGeneratedOnAdd()
                        .HasMaxLength(48);

                    b.Property<string>("accountNumber")
                        .HasMaxLength(60);

                    b.Property<string>("addr")
                        .HasMaxLength(200);

                    b.Property<string>("address")
                        .HasMaxLength(150);

                    b.Property<string>("bank")
                        .HasMaxLength(60);

                    b.Property<string>("clientFPName")
                        .HasMaxLength(120);

                    b.Property<string>("clientLevel")
                        .HasMaxLength(48);

                    b.Property<string>("clientName")
                        .HasMaxLength(120);

                    b.Property<int>("companyId");

                    b.Property<string>("contactName")
                        .HasMaxLength(30);

                    b.Property<int>("convertStatus");

                    b.Property<string>("createdBy")
                        .HasMaxLength(50);

                    b.Property<string>("createdTime")
                        .HasMaxLength(20);

                    b.Property<string>("f0000003")
                        .HasMaxLength(48);

                    b.Property<string>("f0000022")
                        .HasMaxLength(30);

                    b.Property<string>("f0000027")
                        .HasMaxLength(60);

                    b.Property<string>("f0000029")
                        .HasMaxLength(30);

                    b.Property<string>("f0000031");

                    b.Property<string>("f0000033")
                        .HasMaxLength(30);

                    b.Property<string>("f0000034")
                        .HasMaxLength(30);

                    b.Property<string>("f0000036")
                        .HasMaxLength(30);

                    b.Property<string>("f0000037")
                        .HasMaxLength(30);

                    b.Property<string>("f0000038")
                        .HasMaxLength(60);

                    b.Property<string>("f0000039");

                    b.Property<string>("mobile")
                        .HasMaxLength(30);

                    b.Property<string>("modifiedBy")
                        .HasMaxLength(20);

                    b.Property<string>("modifiedTime")
                        .HasMaxLength(20);

                    b.Property<string>("name")
                        .HasMaxLength(120);

                    b.Property<string>("origin")
                        .HasMaxLength(60);

                    b.Property<string>("ownerDeptId")
                        .HasMaxLength(100);

                    b.Property<string>("ownerId")
                        .HasMaxLength(30);

                    b.Property<string>("salesAssistant")
                        .HasMaxLength(60);

                    b.Property<string>("salesOpportunitie")
                        .HasMaxLength(200);

                    b.Property<string>("salesRegion")
                        .HasMaxLength(48);

                    b.Property<int>("status");

                    b.Property<string>("strategicLevel")
                        .HasMaxLength(48);

                    b.Property<DateTime>("synDataDateTime");

                    b.Property<string>("taxID")
                        .HasMaxLength(30);

                    b.Property<string>("workflowInstanceId")
                        .HasMaxLength(50);

                    b.HasKey("objectid");

                    b.ToTable("h3_customer");
                });

            modelBuilder.Entity("MSDB.Models.patent", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("applyDateTime");

                    b.Property<string>("caseStatus")
                        .HasMaxLength(50);

                    b.Property<DateTime>("caseSubmissionDate");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("inventor")
                        .HasMaxLength(200);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("mainType")
                        .HasMaxLength(100);

                    b.Property<string>("mainTypeNo")
                        .HasMaxLength(100);

                    b.Property<string>("patentName")
                        .HasMaxLength(200);

                    b.Property<string>("patentNo")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.HasKey("id");

                    b.ToTable("patent");
                });

            modelBuilder.Entity("MSDB.Models.patent_apply", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("companyAddress")
                        .HasMaxLength(100);

                    b.Property<int>("companyId");

                    b.Property<string>("companyName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("patentNo")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.HasKey("id");

                    b.ToTable("patent_apply");
                });

            modelBuilder.Entity("MSDB.Models.patent_cost", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<decimal>("amount")
                        .HasColumnType("decimal(18,2)");

                    b.Property<string>("costType")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<bool>("isPay");

                    b.Property<bool>("isRemind");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<DateTime>("limitDate");

                    b.Property<string>("patentNo")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime?>("payDateTime");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<DateTime?>("remindDateTime");

                    b.HasKey("id");

                    b.ToTable("patent_cost");
                });

            modelBuilder.Entity("MSDB.Models.product", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("address")
                        .HasMaxLength(100);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("productName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int?>("year");

                    b.HasKey("id");

                    b.ToTable("product");
                });

            modelBuilder.Entity("MSDB.Models.project", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("customerId");

                    b.Property<int>("deptId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("owerId");

                    b.Property<int>("productId");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<int>("year");

                    b.HasKey("id");

                    b.ToTable("project");
                });

            modelBuilder.Entity("MSDB.Models.project_link", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("linkTel")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("linkman")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("mType");

                    b.Property<int>("projectId");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("project_link");
                });

            modelBuilder.Entity("MSDB.Models.project_member", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("mType");

                    b.Property<int>("memberId");

                    b.Property<int>("projectId");

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.HasKey("id");

                    b.ToTable("project_member");
                });

            modelBuilder.Entity("MSDB.Models.sys_log", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("description")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("logParams")
                        .HasMaxLength(1000);

                    b.Property<DateTime>("logTime");

                    b.Property<int>("logType");

                    b.Property<string>("logUrl")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("requestIp")
                        .HasMaxLength(30);

                    b.Property<int>("userId");

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.HasKey("id");

                    b.ToTable("sys_log");
                });

            modelBuilder.Entity("MSDB.Models.sys_session", b =>
                {
                    b.Property<int>("userId");

                    b.Property<DateTime>("createTime");

                    b.Property<DateTime>("expireTime");

                    b.Property<string>("token")
                        .HasMaxLength(50);

                    b.Property<DateTime>("updateTime");

                    b.HasKey("userId");

                    b.ToTable("sys_session");
                });

            modelBuilder.Entity("MSDB.Models.user_dept", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("depId");

                    b.Property<string>("dingUserId")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<bool>("isAdmin");

                    b.Property<string>("unionid")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("user_dept");
                });

            modelBuilder.Entity("MSDB.Models.ys_dept", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("deptName");

                    b.Property<string>("fullPath")
                        .HasMaxLength(300);

                    b.Property<string>("identity")
                        .IsRequired()
                        .HasMaxLength(80);

                    b.Property<int>("level");

                    b.Property<int>("parentId");

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.HasKey("id");

                    b.ToTable("ys_dept");
                });

            modelBuilder.Entity("MSDB.Models.ys_dept_manager", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("depId");

                    b.Property<string>("dingUserId")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("unionid")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.HasKey("id");

                    b.ToTable("ys_dept_manager");
                });

            modelBuilder.Entity("MSDB.Models.ys_user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("avatar")
                        .HasMaxLength(20);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("depId");

                    b.Property<byte>("gender");

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("postion")
                        .HasMaxLength(20);

                    b.Property<bool>("reSetPassword");

                    b.Property<string>("realName")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<byte>("status");

                    b.Property<string>("tel")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("userName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("ys_user");
                });
#pragma warning restore 612, 618
        }
    }
}
