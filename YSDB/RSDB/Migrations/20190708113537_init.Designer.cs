// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using RSDB;

namespace RSDB.Migrations
{
    [DbContext(typeof(DataContext))]
    [Migration("20190708113537_init")]
    partial class init
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

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
