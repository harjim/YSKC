using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class initEsdbTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "config_data",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    type = table.Column<int>(nullable: false),
                    label = table.Column<string>(maxLength: 20, nullable: false),
                    quantity = table.Column<int>(nullable: false),
                    order = table.Column<int>(nullable: false),
                    disabled = table.Column<bool>(nullable: false, defaultValue: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_config_data", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "config_module",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    moduleName = table.Column<string>(maxLength: 20, nullable: false),
                    iconPath = table.Column<string>(maxLength: 200, nullable: false),
                    imagePath = table.Column<string>(maxLength: 200, nullable: false),
                    description = table.Column<string>(maxLength: 300, nullable: false),
                    order = table.Column<int>(nullable: false),
                    disabled = table.Column<bool>(nullable: false, defaultValue: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_config_module", x => x.id);
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
                    description = table.Column<string>(maxLength: 100, nullable: false),
                    logType = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    username = table.Column<string>(maxLength: 100, nullable: false),
                    logTime = table.Column<DateTime>(nullable: false),
                    logUrl = table.Column<string>(maxLength: 100, nullable: false),
                    logParams = table.Column<string>(maxLength: 4000, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    requestIp = table.Column<string>(maxLength: 30, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_log", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    username = table.Column<string>(maxLength: 20, nullable: false),
                    password = table.Column<string>(maxLength: 50, nullable: false),
                    realname = table.Column<string>(maxLength: 30, nullable: true),
                    mobile = table.Column<string>(maxLength: 30, nullable: true),
                    email = table.Column<string>(maxLength: 50, nullable: true),
                    birthday = table.Column<DateTime>(nullable: true),
                    disabled = table.Column<bool>(nullable: false, defaultValue: false),
                    avatar = table.Column<string>(maxLength: 200, nullable: true),
                    gender = table.Column<byte>(nullable: true),
                    idType = table.Column<int>(nullable: true),
                    idNo = table.Column<string>(maxLength: 30, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    types = table.Column<string>(maxLength: 20, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "user_expert",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    types = table.Column<string>(maxLength: 10, nullable: true),
                    transferResult = table.Column<bool>(nullable: false),
                    govReview = table.Column<bool>(nullable: false),
                    govReviewName = table.Column<string>(maxLength: 500, nullable: true),
                    researchResult = table.Column<string>(maxLength: 2000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_expert", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "user_files",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    filename = table.Column<string>(maxLength: 50, nullable: false),
                    filePath = table.Column<string>(maxLength: 200, nullable: false),
                    type = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_files", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "user_info",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    nativePlace = table.Column<string>(maxLength: 50, nullable: false),
                    eduLevel = table.Column<int>(nullable: false),
                    degree = table.Column<int>(nullable: false),
                    titles = table.Column<string>(maxLength: 50, nullable: true),
                    graduatedSchool = table.Column<string>(maxLength: 50, nullable: true),
                    researchArea = table.Column<string>(maxLength: 50, nullable: true),
                    industries = table.Column<string>(maxLength: 50, nullable: true),
                    unitName = table.Column<string>(maxLength: 50, nullable: true),
                    unitType = table.Column<string>(maxLength: 20, nullable: true),
                    job = table.Column<string>(maxLength: 50, nullable: true),
                    unitAddressCode = table.Column<string>(maxLength: 30, nullable: true),
                    unitAddress = table.Column<string>(maxLength: 100, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_info", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "config_data");

            migrationBuilder.DropTable(
                name: "config_module");

            migrationBuilder.DropTable(
                name: "sys_dictionary");

            migrationBuilder.DropTable(
                name: "sys_log");

            migrationBuilder.DropTable(
                name: "user");

            migrationBuilder.DropTable(
                name: "user_expert");

            migrationBuilder.DropTable(
                name: "user_files");

            migrationBuilder.DropTable(
                name: "user_info");
        }
    }
}
