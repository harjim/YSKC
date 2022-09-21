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
    [Migration("20190709004733_company")]
    partial class company
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

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
                        .HasMaxLength(10);

                    b.Property<string>("businessLicense")
                        .IsRequired()
                        .HasMaxLength(80);

                    b.Property<int>("capital");

                    b.Property<string>("companyAddress")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("companyName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("creditCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("depts");

                    b.Property<string>("domain");

                    b.Property<string>("email");

                    b.Property<DateTime?>("firstDevFee");

                    b.Property<bool>("hasDevAccount");

                    b.Property<bool>("highTec");

                    b.Property<string>("highTecIndustry")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("industryCode")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("invoiceTitle")
                        .IsRequired()
                        .HasMaxLength(50);

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

                    b.Property<string>("taxAuthorities")
                        .HasMaxLength(30);

                    b.Property<string>("taxpayerId")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("id");

                    b.ToTable("company");
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

                    b.Property<int>("usefullife");

                    b.HasKey("id");

                    b.ToTable("equipment");
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

                    b.Property<byte>("gender");

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
