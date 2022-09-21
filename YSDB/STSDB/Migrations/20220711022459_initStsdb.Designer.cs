﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using STSDB;

namespace STSDB.Migrations
{
    [DbContext(typeof(DataContext))]
    [Migration("20220711022459_initStsdb")]
    partial class initStsdb
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.6-servicing-10079")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("STSDB.Models.app_menu", b =>
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

                    b.Property<string>("perms")
                        .HasMaxLength(200);

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<byte>("seq");

                    b.Property<byte>("status");

                    b.Property<byte>("type");

                    b.Property<string>("url")
                        .HasMaxLength(200);

                    b.Property<int>("userType");

                    b.HasKey("id");

                    b.ToTable("app_menu");
                });

            modelBuilder.Entity("STSDB.Models.c_user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("companyId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("email")
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("position")
                        .HasMaxLength(20);

                    b.Property<string>("realName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<int>("status");

                    b.Property<string>("tel")
                        .HasMaxLength(30);

                    b.Property<string>("token")
                        .HasMaxLength(50);

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("c_user");
                });

            modelBuilder.Entity("STSDB.Models.company", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<bool?>("Listed");

                    b.Property<string>("addressCode")
                        .HasMaxLength(30);

                    b.Property<string>("addressDetail")
                        .HasMaxLength(100);

                    b.Property<int?>("capital");

                    b.Property<string>("companyName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("finaChief")
                        .HasMaxLength(20);

                    b.Property<string>("finaChiefTel")
                        .HasMaxLength(20);

                    b.Property<bool>("highTech");

                    b.Property<string>("highTechCode")
                        .HasMaxLength(30);

                    b.Property<string>("industryCode")
                        .HasMaxLength(30);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("linkMan")
                        .HasMaxLength(20);

                    b.Property<string>("linkTel")
                        .HasMaxLength(20);

                    b.Property<string>("listedCode")
                        .HasMaxLength(20);

                    b.Property<string>("postCode")
                        .HasMaxLength(10);

                    b.Property<DateTime?>("registerDate");

                    b.Property<DateTime?>("reviewDate")
                        .HasMaxLength(30);

                    b.Property<int>("scale");

                    b.Property<bool?>("smes");

                    b.Property<string>("taxpayerId")
                        .HasMaxLength(30);

                    b.HasKey("id");

                    b.ToTable("company");
                });

            modelBuilder.Entity("STSDB.Models.o_user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("email")
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("orgId");

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("position")
                        .HasMaxLength(20);

                    b.Property<string>("realName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<int>("status");

                    b.Property<string>("tel")
                        .HasMaxLength(30);

                    b.Property<string>("token")
                        .HasMaxLength(50);

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("o_user");
                });

            modelBuilder.Entity("STSDB.Models.organization", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("addressCode")
                        .HasMaxLength(30);

                    b.Property<string>("addressDetail")
                        .HasMaxLength(100);

                    b.Property<DateTime>("createTime");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("orgName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.HasKey("id");

                    b.ToTable("organization");
                });

            modelBuilder.Entity("STSDB.Models.sys_dictionary", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<string>("key")
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("order");

                    b.Property<string>("parentKey")
                        .HasMaxLength(50);

                    b.Property<string>("remark")
                        .HasMaxLength(300);

                    b.Property<byte>("type");

                    b.Property<string>("value")
                        .HasMaxLength(300);

                    b.HasKey("id");

                    b.ToTable("sys_dictionary");
                });

            modelBuilder.Entity("STSDB.Models.sys_log", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("description")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("logParams")
                        .HasMaxLength(4000);

                    b.Property<DateTime>("logTime");

                    b.Property<string>("logUrl")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("requestIp")
                        .HasMaxLength(30);

                    b.Property<int>("userId");

                    b.Property<int>("userType");

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.HasKey("id");

                    b.ToTable("sys_log");
                });
#pragma warning restore 612, 618
        }
    }
}