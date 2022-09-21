﻿// <auto-generated />
using System;
using ESDB;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace ESDB.Migrations
{
    [DbContext(typeof(DataContext))]
    [Migration("20211208004616_createTalent_requirementAndDeliveryTables")]
    partial class createTalent_requirementAndDeliveryTables
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.6-servicing-10079")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("ESDB.Models.achievement", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("achievementName")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("address")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("addressCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<int>("cooperateType");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("description")
                        .IsRequired()
                        .HasMaxLength(2000);

                    b.Property<string>("email")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("endDate");

                    b.Property<int>("fundingType");

                    b.Property<string>("industry")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("innovation")
                        .IsRequired()
                        .HasMaxLength(2000);

                    b.Property<string>("job")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("keywords")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<bool>("negotiation");

                    b.Property<string>("ownerName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<decimal?>("price")
                        .HasColumnType("decimal(18,2)");

                    b.Property<string>("researchArea")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("source")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<DateTime>("startDate");

                    b.Property<int>("status");

                    b.Property<string>("tel")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("trl");

                    b.Property<int>("type");

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("achievement");
                });

            modelBuilder.Entity("ESDB.Models.achievement_files", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("achievementFile")
                        .IsRequired()
                        .HasMaxLength(1000);

                    b.Property<int>("achievementId");

                    b.Property<bool>("agent");

                    b.Property<string>("agentFile")
                        .HasMaxLength(1000);

                    b.Property<bool>("assess");

                    b.Property<string>("assessFile")
                        .HasMaxLength(1000);

                    b.Property<int?>("assessType");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("ownerName")
                        .HasMaxLength(50);

                    b.Property<bool>("pilotTest");

                    b.Property<string>("pilotTestFile")
                        .HasMaxLength(1000);

                    b.Property<bool>("smallTest");

                    b.Property<string>("smallTestFile")
                        .HasMaxLength(1000);

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("achievement_files");
                });

            modelBuilder.Entity("ESDB.Models.achievement_log", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("achievementId");

                    b.Property<DateTime>("createTime");

                    b.Property<int>("msCreatorId");

                    b.Property<int>("status");

                    b.Property<string>("suggestion")
                        .HasMaxLength(2000);

                    b.HasKey("id");

                    b.ToTable("achievement_log");
                });

            modelBuilder.Entity("ESDB.Models.app_menu", b =>
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

                    b.HasKey("id");

                    b.ToTable("app_menu");
                });

            modelBuilder.Entity("ESDB.Models.article", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("author")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("content")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<DateTime>("createTime");

                    b.Property<DateTime>("issueDate");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("msCreatorId");

                    b.Property<int>("msLastUpdatorId");

                    b.Property<string>("source")
                        .HasMaxLength(100);

                    b.Property<string>("title")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("top");

                    b.Property<int>("type");

                    b.HasKey("id");

                    b.ToTable("article");
                });

            modelBuilder.Entity("ESDB.Models.config_data", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<bool>("disabled")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("label")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("msCreatorId");

                    b.Property<int>("msLastUpdatorId");

                    b.Property<int>("order");

                    b.Property<int>("quantity");

                    b.Property<int>("type");

                    b.HasKey("id");

                    b.ToTable("config_data");
                });

            modelBuilder.Entity("ESDB.Models.config_module", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("activeIconPath")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<DateTime>("createTime");

                    b.Property<string>("description")
                        .IsRequired()
                        .HasMaxLength(300);

                    b.Property<bool>("disabled")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("iconPath")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("imagePath")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("moduleName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("msCreatorId");

                    b.Property<int>("msLastUpdatorId");

                    b.Property<int>("order");

                    b.HasKey("id");

                    b.ToTable("config_module");
                });

            modelBuilder.Entity("ESDB.Models.e_cooperation", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int?>("creatorId");

                    b.Property<string>("email")
                        .HasMaxLength(50);

                    b.Property<string>("fullname")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("information")
                        .HasMaxLength(500);

                    b.Property<int>("intentionId");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("linkInfo")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int?>("msLastUpdatorId");

                    b.Property<string>("remark")
                        .HasMaxLength(500);

                    b.Property<int>("status");

                    b.Property<int>("type");

                    b.Property<string>("unitName")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.HasKey("id");

                    b.ToTable("e_cooperation");
                });

            modelBuilder.Entity("ESDB.Models.sys_dictionary", b =>
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

            modelBuilder.Entity("ESDB.Models.sys_log", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("description")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("logParams")
                        .HasMaxLength(4000);

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

            modelBuilder.Entity("ESDB.Models.talent_delivery", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("talentId");

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.HasIndex("userId", "talentId")
                        .IsUnique();

                    b.ToTable("talent_delivery");
                });

            modelBuilder.Entity("ESDB.Models.talent_requirement", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("address")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("addressCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<DateTime>("closeDate");

                    b.Property<DateTime>("createTime");

                    b.Property<string>("customerName")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("duty")
                        .IsRequired()
                        .HasMaxLength(1000);

                    b.Property<int>("eduLevel");

                    b.Property<string>("job")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<int>("jobType");

                    b.Property<string>("keywords")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("linkEmail")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("linkName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("linkTel")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<decimal?>("maxSalary")
                        .HasColumnType("decimal(18,2)");

                    b.Property<decimal?>("minSalary")
                        .HasColumnType("decimal(18,2)");

                    b.Property<int>("msCreatorId");

                    b.Property<int>("msLastUpdatorId");

                    b.Property<bool>("negotiation");

                    b.Property<string>("position")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int>("recruitment");

                    b.Property<string>("requirement")
                        .IsRequired()
                        .HasMaxLength(1000);

                    b.Property<int>("status");

                    b.Property<int>("workYear");

                    b.HasKey("id");

                    b.ToTable("talent_requirement");
                });

            modelBuilder.Entity("ESDB.Models.tech_requirement", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("address")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("addressCode")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("background")
                        .HasMaxLength(2000);

                    b.Property<decimal?>("budget")
                        .HasColumnType("decimal(18,2)");

                    b.Property<DateTime>("closeDate");

                    b.Property<int>("cooperateType");

                    b.Property<DateTime>("createTime");

                    b.Property<string>("customerName")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("difficulty")
                        .HasMaxLength(2000);

                    b.Property<string>("industry")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("keywords")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("linkEmail")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("linkName")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("linkTel")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<int>("msCreatorId");

                    b.Property<int>("msLastUpdatorId");

                    b.Property<bool>("negotiation");

                    b.Property<string>("position")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("requirementName")
                        .IsRequired()
                        .HasMaxLength(100);

                    b.Property<string>("researchArea")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("status");

                    b.Property<string>("target")
                        .HasMaxLength(2000);

                    b.Property<int>("urgency");

                    b.HasKey("id");

                    b.ToTable("tech_requirement");
                });

            modelBuilder.Entity("ESDB.Models.user", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("avatar")
                        .HasMaxLength(200);

                    b.Property<DateTime?>("birthday");

                    b.Property<DateTime>("createTime");

                    b.Property<bool>("disabled")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("email")
                        .HasMaxLength(50);

                    b.Property<byte?>("gender");

                    b.Property<string>("idNo")
                        .HasMaxLength(30);

                    b.Property<int?>("idType");

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<string>("mobile")
                        .HasMaxLength(30);

                    b.Property<string>("password")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("realname")
                        .HasMaxLength(30);

                    b.Property<string>("remark")
                        .HasMaxLength(200);

                    b.Property<string>("types")
                        .HasMaxLength(20);

                    b.Property<string>("username")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.HasKey("id");

                    b.ToTable("user");
                });

            modelBuilder.Entity("ESDB.Models.user_expert", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<bool>("govReview");

                    b.Property<string>("govReviewName")
                        .HasMaxLength(500);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("researchResult")
                        .HasMaxLength(2000);

                    b.Property<int>("status");

                    b.Property<bool>("transferResult");

                    b.Property<string>("types")
                        .HasMaxLength(10);

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("user_expert");
                });

            modelBuilder.Entity("ESDB.Models.user_expert_log", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("msCreatorId");

                    b.Property<int>("status");

                    b.Property<string>("suggestion")
                        .HasMaxLength(2000);

                    b.Property<int>("userExpertId");

                    b.HasKey("id");

                    b.ToTable("user_expert_log");
                });

            modelBuilder.Entity("ESDB.Models.user_files", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<string>("filePath")
                        .IsRequired()
                        .HasMaxLength(200);

                    b.Property<string>("filename")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<int>("type");

                    b.Property<int>("userId");

                    b.HasKey("id");

                    b.ToTable("user_files");
                });

            modelBuilder.Entity("ESDB.Models.user_info", b =>
                {
                    b.Property<int>("id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("createTime");

                    b.Property<int>("creatorId");

                    b.Property<int>("degree");

                    b.Property<string>("deptName")
                        .HasMaxLength(50);

                    b.Property<int>("eduLevel");

                    b.Property<string>("graduatedSchool")
                        .HasMaxLength(50);

                    b.Property<string>("industries")
                        .HasMaxLength(50);

                    b.Property<string>("job")
                        .HasMaxLength(50);

                    b.Property<DateTime>("lastUpdateTime");

                    b.Property<int>("lastUpdatorId");

                    b.Property<string>("nativePlace")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("researchArea")
                        .HasMaxLength(50);

                    b.Property<string>("titles")
                        .HasMaxLength(50);

                    b.Property<string>("unitAddress")
                        .HasMaxLength(100);

                    b.Property<string>("unitAddressCode")
                        .HasMaxLength(30);

                    b.Property<string>("unitName")
                        .HasMaxLength(50);

                    b.Property<string>("unitType")
                        .HasMaxLength(20);

                    b.Property<int>("userId");

                    b.Property<string>("workExperience")
                        .HasColumnType("text");

                    b.HasKey("id");

                    b.ToTable("user_info");
                });
#pragma warning restore 612, 618
        }
    }
}
