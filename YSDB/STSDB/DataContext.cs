using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations;
using STSDB.Models;

namespace STSDB
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) :
            base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder
                .Entity<c_year_info>()
                .HasIndex(a => new { a.companyId, a.year })
                .IsUnique(true);
            modelBuilder
                .Entity<c_apply>()
                .HasIndex(a => new { a.companyId, a.projectId, a.year })
                .IsUnique(true);
            modelBuilder
                .Entity<c_project_funds>()
                .HasIndex(a => new { a.companyId, a.projectId, a.month })
                .IsUnique(true);
            modelBuilder
                .Entity<c_project_year>()
                .HasIndex(a => new { a.companyId, a.projectId, a.year })
                .IsUnique(true);
        }

        public static void InitData(MigrationBuilder migrationBuilder)
        {
            var dUser = "ysAdmin";
            var dPwd = "ysAdmin!@#$";
            string hashPwd = null;
            using (var md5 = MD5.Create())
            {
                var result =
                    md5.ComputeHash(Encoding.UTF8.GetBytes(dUser + dPwd));
                hashPwd = BitConverter.ToString(result).Replace("-", "");
            }
            migrationBuilder
                .InsertData("o_user",
                new []
                {
                    "userName",
                    "password",
                    "realName",
                    "status",
                    "orgId",
                    "remark",
                    "creatorId",
                    "createTime",
                    "lastUpdatorId",
                    "lastUpdateTime"
                },
                new object[] {
                    dUser,
                    hashPwd,
                    "管理员",
                    0,
                    0,
                    "初始用户",
                    -1,
                    DateTime.Now,
                    -1,
                    DateTime.Now
                });
        }

        public DbSet<sys_log> sys_log { get; set; }

        public DbSet<organization> organization { get; set; }

        public DbSet<o_user> o_user { get; set; }

        public DbSet<company> company { get; set; }

        public DbSet<c_user> c_user { get; set; }

        public DbSet<sys_dictionary> sys_dictionary { get; set; }

        public DbSet<app_menu> app_menu { get; set; }

        public DbSet<c_year_info> c_year_info { get; set; }

        public DbSet<c_project> c_project { get; set; }

        public DbSet<c_project_funds> c_project_funds { get; set; }

        public DbSet<c_project_year> c_project_year { get; set; }

        public DbSet<c_apply> c_apply { get; set; }

        public DbSet<c_highTech> c_highTech { get; set; }
    }
}
