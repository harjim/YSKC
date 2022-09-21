using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createProject_registerTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_register",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    registerName = table.Column<string>(maxLength: 100, nullable: false),
                    beginDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    changeContent = table.Column<string>(maxLength: 200, nullable: true),
                    changeStatus = table.Column<int>(nullable: true),
                    investAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    finishDate = table.Column<DateTime>(nullable: true),
                    reportStatus = table.Column<int>(nullable: true),
                    schemeStatus = table.Column<int>(nullable: true),
                    dockStatus = table.Column<int>(nullable: true),
                    originalPlace = table.Column<string>(maxLength: 200, nullable: true),
                    filePath = table.Column<string>(maxLength: 200, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_register", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_register");
        }
    }
}
