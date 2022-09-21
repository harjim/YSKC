using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createCustomerService : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "customer_service",
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
                    mainStaffId = table.Column<int>(nullable: false),
                    otherStaffIds = table.Column<string>(maxLength: 50, nullable: true),
                    totalTime = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalStaff = table.Column<int>(nullable: false),
                    lastProblem = table.Column<string>(maxLength: 800, nullable: true),
                    nowProblem = table.Column<string>(maxLength: 800, nullable: true),
                    customerAdvice = table.Column<string>(maxLength: 800, nullable: true),
                    customerParticipan = table.Column<string>(maxLength: 50, nullable: true),
                    serviceDept = table.Column<string>(maxLength: 50, nullable: true),
                    status = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_customer_service", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "customer_service");
        }
    }
}
