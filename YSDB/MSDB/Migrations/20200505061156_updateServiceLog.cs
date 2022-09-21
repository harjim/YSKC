using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class updateServiceLog : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "customer_service");

            migrationBuilder.CreateTable(
                name: "serviceLog",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    serviceType = table.Column<int>(nullable: false),
                    totalTime = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalStaff = table.Column<int>(nullable: false),
                    lastProblem = table.Column<string>(maxLength: 800, nullable: true),
                    nowProblem = table.Column<string>(maxLength: 800, nullable: true),
                    customerAdvice = table.Column<string>(maxLength: 800, nullable: true),
                    customerParticipan = table.Column<string>(maxLength: 100, nullable: true),
                    serviceDept = table.Column<string>(maxLength: 100, nullable: true),
                    status = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_serviceLog", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "serviceLog");

            migrationBuilder.CreateTable(
                name: "customer_service",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    customerAdvice = table.Column<string>(maxLength: 800, nullable: true),
                    customerParticipan = table.Column<string>(maxLength: 50, nullable: true),
                    lastProblem = table.Column<string>(maxLength: 800, nullable: true),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    mainStaffId = table.Column<int>(nullable: false),
                    nowProblem = table.Column<string>(maxLength: 800, nullable: true),
                    otherStaffIds = table.Column<string>(maxLength: 50, nullable: true),
                    serviceDept = table.Column<string>(maxLength: 50, nullable: true),
                    serviceType = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    totalStaff = table.Column<int>(nullable: false),
                    totalTime = table.Column<decimal>(type: "decimal(18,2)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_customer_service", x => x.id);
                });
        }
    }
}
