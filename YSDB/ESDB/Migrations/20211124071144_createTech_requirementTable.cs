using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class createTech_requirementTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "intentionUserId",
                table: "e_cooperation",
                newName: "type");

            migrationBuilder.AddColumn<int>(
                name: "intentionId",
                table: "e_cooperation",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<decimal>(
                name: "price",
                table: "achievement",
                type: "decimal(18,2)",
                nullable: true,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,6)",
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "tech_requirement",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    status = table.Column<int>(nullable: false),
                    requirementName = table.Column<int>(nullable: false),
                    industry = table.Column<string>(maxLength: 20, nullable: false),
                    researchArea = table.Column<string>(maxLength: 20, nullable: false),
                    cooperateType = table.Column<int>(nullable: false),
                    urgency = table.Column<int>(nullable: false),
                    closeDate = table.Column<DateTime>(nullable: false),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    keywords = table.Column<string>(maxLength: 50, nullable: false),
                    customerName = table.Column<string>(maxLength: 100, nullable: false),
                    linkName = table.Column<string>(maxLength: 20, nullable: false),
                    position = table.Column<string>(maxLength: 50, nullable: false),
                    linkTel = table.Column<string>(maxLength: 50, nullable: false),
                    linkEmail = table.Column<string>(maxLength: 50, nullable: false),
                    addressCode = table.Column<string>(maxLength: 30, nullable: false),
                    address = table.Column<string>(maxLength: 200, nullable: false),
                    background = table.Column<string>(maxLength: 2000, nullable: true),
                    difficulty = table.Column<string>(maxLength: 2000, nullable: true),
                    target = table.Column<string>(maxLength: 2000, nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tech_requirement", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tech_requirement");

            migrationBuilder.DropColumn(
                name: "intentionId",
                table: "e_cooperation");

            migrationBuilder.RenameColumn(
                name: "type",
                table: "e_cooperation",
                newName: "intentionUserId");

            migrationBuilder.AlterColumn<decimal>(
                name: "price",
                table: "achievement",
                type: "decimal(18,6)",
                nullable: true,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)",
                oldNullable: true);
        }
    }
}
