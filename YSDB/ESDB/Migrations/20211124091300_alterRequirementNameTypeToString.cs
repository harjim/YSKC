using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class alterRequirementNameTypeToString : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "requirementName",
                table: "tech_requirement",
                maxLength: 100,
                nullable: false,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "requirementName",
                table: "tech_requirement",
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 100);
        }
    }
}
