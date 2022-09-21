using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class createTalent_requirementAndDeliveryTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "workExperience",
                table: "user_info",
                type: "text",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "talent_delivery",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    talentId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_talent_delivery", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "talent_requirement",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    job = table.Column<string>(maxLength: 100, nullable: false),
                    minSalary = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    maxSalary = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    negotiation = table.Column<bool>(nullable: false),
                    jobType = table.Column<int>(nullable: false),
                    workYear = table.Column<int>(nullable: false),
                    eduLevel = table.Column<int>(nullable: false),
                    recruitment = table.Column<int>(nullable: false),
                    closeDate = table.Column<DateTime>(nullable: false),
                    keywords = table.Column<string>(maxLength: 50, nullable: false),
                    customerName = table.Column<string>(maxLength: 100, nullable: false),
                    linkName = table.Column<string>(maxLength: 20, nullable: false),
                    position = table.Column<string>(maxLength: 50, nullable: false),
                    linkTel = table.Column<string>(maxLength: 50, nullable: false),
                    linkEmail = table.Column<string>(maxLength: 50, nullable: false),
                    addressCode = table.Column<string>(maxLength: 30, nullable: false),
                    address = table.Column<string>(maxLength: 200, nullable: false),
                    duty = table.Column<string>(maxLength: 1000, nullable: false),
                    requirement = table.Column<string>(maxLength: 1000, nullable: false),
                    status = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_talent_requirement", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_talent_delivery_userId_talentId",
                table: "talent_delivery",
                columns: new[] { "userId", "talentId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "talent_delivery");

            migrationBuilder.DropTable(
                name: "talent_requirement");

            migrationBuilder.DropColumn(
                name: "workExperience",
                table: "user_info");
        }
    }
}
