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
    [Migration("20191015022601_dept_int")]
    partial class dept_int
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
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

                    b.Property<string>("capital")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("companyAddress")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("companyId");

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

                    b.Property<string>("taxAuthorities")
                        .HasMaxLength(30);

                    b.Property<string>("taxpayerId")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("id");

                    b.ToTable("customer");
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