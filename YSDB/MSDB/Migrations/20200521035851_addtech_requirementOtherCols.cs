using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addtech_requirementOtherCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "tech_requirement",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 100);

            migrationBuilder.AlterColumn<string>(
                name: "cooperateType",
                table: "tech_requirement",
                maxLength: 50,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 100);

            migrationBuilder.AddColumn<string>(
                name: "otherCooperateType",
                table: "tech_requirement",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "otherDomain",
                table: "tech_requirement",
                maxLength: 50,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "otherCooperateType",
                table: "tech_requirement");

            migrationBuilder.DropColumn(
                name: "otherDomain",
                table: "tech_requirement");

            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "tech_requirement",
                maxLength: 100,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50);

            migrationBuilder.AlterColumn<string>(
                name: "cooperateType",
                table: "tech_requirement",
                maxLength: 100,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50);
        }
    }
}
