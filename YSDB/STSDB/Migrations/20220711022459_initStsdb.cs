using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class initStsdb : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "app_menu",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    parentId = table.Column<int>(nullable: false),
                    name = table.Column<string>(maxLength: 30, nullable: false),
                    type = table.Column<byte>(nullable: false),
                    level = table.Column<byte>(nullable: false),
                    url = table.Column<string>(maxLength: 200, nullable: true),
                    icon = table.Column<string>(maxLength: 30, nullable: false),
                    perms = table.Column<string>(maxLength: 200, nullable: true),
                    status = table.Column<byte>(nullable: false),
                    seq = table.Column<byte>(nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    fullPath = table.Column<string>(maxLength: 300, nullable: true),
                    userType = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_menu", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    username = table.Column<string>(maxLength: 20, nullable: false),
                    password = table.Column<string>(maxLength: 50, nullable: false),
                    realName = table.Column<string>(maxLength: 50, nullable: false),
                    status = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    token = table.Column<string>(maxLength: 50, nullable: true),
                    position = table.Column<string>(maxLength: 20, nullable: true),
                    tel = table.Column<string>(maxLength: 30, nullable: true),
                    email = table.Column<string>(maxLength: 50, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_user", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "company",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyName = table.Column<string>(maxLength: 50, nullable: false),
                    scale = table.Column<int>(nullable: false),
                    registerDate = table.Column<DateTime>(nullable: true),
                    capital = table.Column<int>(nullable: true),
                    addressCode = table.Column<string>(maxLength: 30, nullable: true),
                    addressDetail = table.Column<string>(maxLength: 100, nullable: true),
                    industryCode = table.Column<string>(maxLength: 30, nullable: true),
                    Listed = table.Column<bool>(nullable: true),
                    listedCode = table.Column<string>(maxLength: 20, nullable: true),
                    highTech = table.Column<bool>(nullable: false),
                    highTechCode = table.Column<string>(maxLength: 30, nullable: true),
                    reviewDate = table.Column<DateTime>(maxLength: 30, nullable: true),
                    smes = table.Column<bool>(nullable: true),
                    linkMan = table.Column<string>(maxLength: 20, nullable: true),
                    linkTel = table.Column<string>(maxLength: 20, nullable: true),
                    postCode = table.Column<string>(maxLength: 10, nullable: true),
                    finaChief = table.Column<string>(maxLength: 20, nullable: true),
                    finaChiefTel = table.Column<string>(maxLength: 20, nullable: true),
                    taxpayerId = table.Column<string>(maxLength: 30, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_company", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "o_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    username = table.Column<string>(maxLength: 20, nullable: false),
                    password = table.Column<string>(maxLength: 50, nullable: false),
                    realName = table.Column<string>(maxLength: 50, nullable: false),
                    status = table.Column<int>(nullable: false),
                    orgId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    token = table.Column<string>(maxLength: 50, nullable: true),
                    position = table.Column<string>(maxLength: 20, nullable: true),
                    tel = table.Column<string>(maxLength: 30, nullable: true),
                    email = table.Column<string>(maxLength: 50, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_o_user", x => x.id);
                });
            DataContext.InitData(migrationBuilder);
            migrationBuilder.CreateTable(
                name: "organization",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    orgName = table.Column<string>(maxLength: 50, nullable: false),
                    addressCode = table.Column<string>(maxLength: 30, nullable: true),
                    addressDetail = table.Column<string>(maxLength: 100, nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_organization", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "sys_dictionary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    type = table.Column<byte>(nullable: false),
                    key = table.Column<string>(maxLength: 50, nullable: true),
                    value = table.Column<string>(maxLength: 300, nullable: true),
                    parentKey = table.Column<string>(maxLength: 50, nullable: true),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    order = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_dictionary", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "sys_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    username = table.Column<string>(maxLength: 100, nullable: false),
                    userType = table.Column<int>(nullable: false),
                    logTime = table.Column<DateTime>(nullable: false),
                    logUrl = table.Column<string>(maxLength: 100, nullable: false),
                    logParams = table.Column<string>(maxLength: 4000, nullable: true),
                    requestIp = table.Column<string>(maxLength: 30, nullable: true),
                    description = table.Column<string>(maxLength: 100, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "app_menu");

            migrationBuilder.DropTable(
                name: "c_user");

            migrationBuilder.DropTable(
                name: "company");

            migrationBuilder.DropTable(
                name: "o_user");

            migrationBuilder.DropTable(
                name: "organization");

            migrationBuilder.DropTable(
                name: "sys_dictionary");

            migrationBuilder.DropTable(
                name: "sys_log");
        }
    }
}
